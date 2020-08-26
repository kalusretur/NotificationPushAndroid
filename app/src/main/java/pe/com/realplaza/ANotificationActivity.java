package pe.com.realplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ANotificationActivity extends AppCompatActivity {

    TextView nameForm, lasNameForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_notification);

        nameForm = (TextView)findViewById(R.id.textView7);
        lasNameForm = (TextView)findViewById(R.id.textView9);

        Intent intent_o = getIntent();
        String name = intent_o.getStringExtra("name");
        String last_name = intent_o.getStringExtra("last_name");

        nameForm.setText(name);
        lasNameForm.setText(last_name);

        System.err.println("Print Notification A : "+ name);
        System.err.println("Print Notification A : "+ last_name);
    }
}
