package example.com.androidcontact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends Activity {
    ListView list;
    TextView totalContact;

    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        totalContact = (TextView) findViewById(R.id.totalContact);
        list = (ListView) findViewById(R.id.listView1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        if (!isLoaded) {
            isLoaded = true;
            LoadContacts lca = new LoadContacts();
            lca.execute();
        }
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        LoadContacts lca = new LoadContacts();
        lca.execute();
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

            pd = ProgressDialog.show(HomeActivity.this, "Loading Contacts", "Please Wait");
        }

        @Override
        protected List<AppContact> doInBackground(Void... params) {

//            List<AppContact> contacts = AppContact.listAll(AppContact.class);
            List<AppContact> contacts = AppContact.listAll(AppContact.class, "rating DESC");

//            List<AppContact> contacts = AppContact.findWithQuery(AppContact.class, "select * from AppContact order by rating limit 20  WHERE ROWNUM > " + 20 * count);
//            List<AppContact> contacts = AppContact.findWithQuery(AppContact.class, "select * from AppContact order by rating limit 0,20 ");

//            contacts = AppContact.find(AppContact.class, "name=? and number=? and rating=?", new String[]{"Test Name", "1234567890"}, null, "rating", "20");

//            contacts = AppContact.find(AppContact.class, "id=? and name=? and number=? and rating=?", new String[]{"0","Test Name", "1234567890","0"}, null, "rating", "20");

            return contacts;
        }

        @Override
        protected void onPostExecute(List<AppContact> contacts) {
            super.onPostExecute(contacts);
            pd.cancel();
            totalContact.setText("Total Contact " + contacts.size());// + " pageCount" + pageCount
            list.setAdapter(new AppAdapter(contacts, HomeActivity.this));
        }
    }

}