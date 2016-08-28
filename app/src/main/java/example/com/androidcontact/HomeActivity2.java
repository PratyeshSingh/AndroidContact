package example.com.androidcontact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity2 extends Activity {
    private ListView list;
    private TextView totalContact;
    private final List<AppContact> contactList = new ArrayList<>();
    private AppAdapter adapter;

    private final int ActionActivityRequest = 9876;

    //variable for Orientation change
    private boolean isLoaded = false;


    // Variables for scroll listener
    int mVisibleThreshold = 20;
    int mCurrentPage = 0;
    boolean loadMore = true;
    boolean userScrolled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        totalContact = (TextView) findViewById(R.id.totalContact);
        list = (ListView) findViewById(R.id.listView1);

        adapter = new AppAdapter(contactList, HomeActivity2.this);

        list.setAdapter(adapter);

        implementScrollListener();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppContact appContact = adapter.getItem(position);

                Intent intent = new Intent(HomeActivity2.this, ActionActivity.class);
                intent.putExtra("id", (long) appContact.getId());
                startActivityForResult(intent, ActionActivityRequest);

                adapter.getItem(position).setRating(appContact.getRating() + 1);
            }
        });

        if (!isLoaded) {
            isLoaded = true;
            mCurrentPage++;
            LoadContacts lca = new LoadContacts();
            lca.execute();
        }
    }


    // Implement scroll listener
    private void implementScrollListener() {
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                // If scroll state is touch scroll then set userScrolled
                // true
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Now check if userScrolled is true and also check if
                // the item is end then update list view and set
                // userScrolled to false
                if (userScrolled && firstVisibleItem + visibleItemCount == totalItemCount) {

                    userScrolled = false;
                    updateListView();
                }
            }
        });
    }

    // Method for repopulating recycler view
    private void updateListView() {
        if (loadMore) {
            mCurrentPage++;
            LoadContacts lca = new LoadContacts();
            lca.execute();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //your code
            System.out.print("ORIENTATION_PORTRAIT");
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //your code
            System.out.print("ORIENTATION_LANDSCAPE");
        }
    }


    class LoadContacts extends AsyncTask<Void, Void, List<AppContact>> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(HomeActivity2.this, "Loading Contacts", "Please Wait");
        }

        @Override
        protected List<AppContact> doInBackground(Void... params) {

//            List<AppContact> contacts = AppContact.listAll(AppContact.class);

//            List<AppContact> contacts = AppContact.listAll(AppContact.class, "rating DESC");

//            List<AppContact> contacts = AppContact.findWithQuery(AppContact.class, "Select * from APP_CONTACT order by rating DESC limit ?", mVisibleThreshold * mCurrentPage + "");

//
            Select specificAuthorQuery = Select.from(AppContact.class)
                    /*.where(Condition.prop("rating")
                    .gt(mCurrentPage * mVisibleThreshold)*//*, Condition.prop("age").lt(50)*//*)*/
                    .orderBy("rating desc")
                    .limit(mVisibleThreshold * mCurrentPage + "");

            List<AppContact> contacts = specificAuthorQuery.list();
            if (specificAuthorQuery.count() < mVisibleThreshold * mCurrentPage) {
                loadMore = false;
            }


//            contacts = AppContact.find(AppContact.class, "name=? and number=? and rating=?", new String[]{"Test Name", "1234567890"}, null, "rating", "20");

            return contacts;
        }

        @Override
        protected void onPostExecute(List<AppContact> contacts) {
            super.onPostExecute(contacts);
            pd.cancel();

            contactList.clear();
            contactList.addAll(contacts);

            totalContact.setText("Total Contact " + contactList.size() + " & Total Page Count" + mCurrentPage);

//            contactList.addAll(contacts);
            adapter.refreshList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Collections.sort(contactList);
        adapter.refreshList();

    }
}