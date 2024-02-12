package com.example.todotoday;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class TodoNotification extends Service {

    public static final String EXTRA_TASK_NAME = "taskName";


    public TodoNotification() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate()
    {
        NotificationManager notifyMng = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n;

        // a pending intent is used to indicate that we want the activity to be started if the user taps the notification
        Intent intent = new Intent(this, TodoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        // check to see if the device is API 26 or higher, which requires Channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) // O == Oreo (26)
        {
            // configure a channel
            String channel = "1111";
            CharSequence name = "channel1111";
            int importance = NotificationManager.IMPORTANCE_LOW; // importance level
            NotificationChannel nChannel = new NotificationChannel(channel, name, importance);

            // add channel
            notifyMng.createNotificationChannel(nChannel);

            //String taskName = getIntent().getStringExtra(EXTRA_TASK_NAME);

            // configure a new notification using a builder object
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setTicker("Ticker Message Go Here")
                    .setContentTitle("Title To do tu du ta da te de") // Put the taskName in here
                    .setContentText("New Message Text")
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);
            n = builder.build();
        }
        else
        {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setTicker("Ticker Message Go Here")
                    .setContentTitle("New Message Title")
                    .setContentText("New Message Text")
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);
            n = builder.build();
        }
        notifyMng.notify(1, n);

    }
}
