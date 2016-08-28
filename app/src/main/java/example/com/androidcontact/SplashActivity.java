package example.com.androidcontact;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SyncContacts lca = new SyncContacts();
        lca.execute();
    }


    class SyncContacts extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(SplashActivity.this, "Syncing Contacts", "Please Wait");
        }

        @Override
        protected Void doInBackground(Void... params) {

            Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (c.moveToNext()) {

                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                try {
                    if (phNumber != null && !phNumber.contains("#")) {

                        List<AppContact> checkContact = AppContact.find(AppContact.class, "number = ?", phNumber);

                        if (checkContact != null && checkContact.size() > 0) {
                            AppContact appContact = checkContact.get(0);
                            appContact = new AppContact(contactName, phNumber, appContact.getRating());
                            appContact.update();
                        } else {
                            AppContact appContact = new AppContact(contactName, phNumber, 0);
                            appContact.save();
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            c.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.cancel();

//            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            Intent intent = new Intent(SplashActivity.this, HomeActivity2.class);
            startActivity(intent);
            finish();
        }
    }
}
