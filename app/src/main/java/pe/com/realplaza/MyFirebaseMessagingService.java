package pe.com.realplaza;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {
        String name = "";
        String last_name = "";
        Map<String, String> data = new HashMap<String, String>();
        String id = "";
        String product = "";
        String description = "";

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            name = remoteMessage.getData().get("name");
            last_name = remoteMessage.getData().get("last_name");
            id = remoteMessage.getData().get("id");
            product = remoteMessage.getData().get("product");
            description = remoteMessage.getData().get("description");
            data = remoteMessage.getData();
            JSONObject object = new JSONObject(data);
            Log.e("JSON_OBJECT", object.toString());

            //System.err.println("Notification" + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    // code goes here
                    Toast.makeText(getBaseContext(), remoteMessage.getData().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            ShowNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("product"),"pe.com.realplaza_NotificationB", name, last_name, id, product, description);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            //System.err.println("Data" + remoteMessage.getData());
            //System.err.println("Notification" + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String click_action = remoteMessage.getNotification().getClickAction();
            ShowNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),click_action, name, last_name, id, product, description);
            /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    // code goes here
                    Toast.makeText(getBaseContext(), remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
                }
            });*/

            //String click_action = remoteMessage.getNotification().getClickAction();
            //sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), name, last_name, id, product, description, click_action);
            //Intent intent = new Intent(click_action);


        }
    }

    private void sendNotification(String messageBody, String messageTitle, String name, String last_name, String id, String product, String description, String click_action) {
        Intent intent = new Intent(click_action);
        intent.putExtra("name", name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("id", id);
        intent.putExtra("product", product);
        intent.putExtra("description", description);
    }

    private void ShowNotification(String title, String body, String Activity, String name, String last_name, String id, String product, String description) {
        String idx = "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,idx);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(idx, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert  nm != null;
            nm.createNotificationChannel(nc);
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.logo_morado)
                .setContentText(body)
                .setContentIntent(clicknoti(Activity, name, last_name, id,product,description))
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());
    }

    public PendingIntent clicknoti(String Activity, String name, String last_name, String id, String product, String description){
        Intent nf = new Intent(Activity);
        nf.putExtra("name", name);
        nf.putExtra("last_name", last_name);
        nf.putExtra("id", id);
        nf.putExtra("product", product);
        nf.putExtra("description", description);
        nf.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
