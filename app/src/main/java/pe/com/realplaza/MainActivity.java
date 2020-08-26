package pe.com.realplaza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    Button btnGetToken, btnSuscription;
    EditText EdiTextoken, EditTextSuscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetToken = (Button)findViewById(R.id.button);
        EdiTextoken = (EditText)findViewById(R.id.editText);
        btnSuscription = (Button)findViewById(R.id.button2);
        EditTextSuscription = (EditText)findViewById(R.id.editText2);


        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nofitication();
            }
        });

        btnSuscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextSuscription.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "El Nombre del Topic no puede ser Vacío.", Toast.LENGTH_SHORT).show();
                    EditTextSuscription.requestFocus();
                } else {
                    if(EditTextSuscription.getText().toString().trim().length() == 0) {
                        Toast.makeText(MainActivity.this, "El Nombre del Topic no puede ser Espacios en Blanco.", Toast.LENGTH_SHORT).show();
                        EditTextSuscription.setText("");
                        EditTextSuscription.requestFocus();
                    } else {
                        Suscription(EditTextSuscription.getText().toString());
                    }
                }
            }
        });

    }

    private void Nofitication() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    //Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken();
                EdiTextoken.setText(token);
                System.err.println("Token : "+ token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Suscription(String subcrition){
        FirebaseMessaging.getInstance().subscribeToTopic(subcrition)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Suscripción Correctamente";
                        if (!task.isSuccessful()) {
                            msg = "Ocurrio un error en la Suscripción";
                        }
                        //Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
