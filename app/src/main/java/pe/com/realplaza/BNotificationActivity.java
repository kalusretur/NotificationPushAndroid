package pe.com.realplaza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class BNotificationActivity extends AppCompatActivity {

    TextView idForm,productForm,descriptionForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_notification);

        idForm = (TextView)findViewById(R.id.textView11);
        productForm = (TextView)findViewById(R.id.textView13);
        descriptionForm = (TextView)findViewById(R.id.textView15);

        Intent intent_o = getIntent();
        String id = intent_o.getStringExtra("id");
        String product = intent_o.getStringExtra("product");
        String description = intent_o.getStringExtra("description");

        idForm.setText(id);
        productForm.setText(product);
        descriptionForm.setText(description);

        System.err.println("Print Notification B : "+ id);
        System.err.println("Print Notification B : "+ product);
        System.err.println("Print Notification B : "+ description);
    }
}
