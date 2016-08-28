package example.com.androidcontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);

        TextView name = (TextView) findViewById(R.id.name);
        TextView number = (TextView) findViewById(R.id.number);
        TextView rating = (TextView) findViewById(R.id.rating);

        AppContact appContact = AppContact.findById(AppContact.class, id);
        appContact.setRating(appContact.getRating() + 1);
        appContact.update();

        name.setText(appContact.getName());
        rating.setText(appContact.getRating() + "");
        number.setText(appContact.getNumber());

    }
}
