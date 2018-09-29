package com.practice.leo.pushnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button notificationBtn;
    Button customNotificationButton;
    private static final int NOTIFICATION_ID_OPEN=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationBtn = findViewById(R.id.notification_button);
        customNotificationButton = findViewById(R.id.custom_notification_button);



        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builderNoti =new NotificationCompat.Builder(getBaseContext());
                NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

                Intent notificationIntent =new Intent(getBaseContext(),MainActivity.class);

                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );

                PendingIntent pendingIntent= PendingIntent.getActivity(getBaseContext(),0,notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                builderNoti.setContentIntent(pendingIntent);

               builderNoti.setSmallIcon(R.drawable.ic_notification);
             //   builderNoti.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name));
                builderNoti.setAutoCancel(true);
                builderNoti.setContentTitle("Ai Robotics");
                builderNoti.setContentText("Hi! this is ai robotics of bangladesh. A new era of robotics has already started in bangladesh ");

                notificationManager.notify(NOTIFICATION_ID_OPEN, builderNoti.build());


            }
        });

        customNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotification();

            }
        });


    }

    private void createNotification() {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);

        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);  //END_INCLUDE Intent//

        //BEGIN_Intent TICKER
        //sets ticker text

      //  builder.setTicker("This is Custom Notification");
        builder.setContentTitle("Techkarkhana.com");

        builder.setSmallIcon(R.drawable.ic_notification);

        //END_INCLUDE_TICKER

        builder.setAutoCancel(true);

        //BUILD Notification

        Notification notification= builder.build();


        //BEGIN_CustomLayout

        RemoteViews contentView =new RemoteViews(getPackageName(),R.layout.notification);

        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        final String text = getResources().getString(R.string.collapsed, time);
        contentView.setTextViewText(R.id.textView, text);

        notification.contentView=contentView;




        //add expandable notification if supported

        if (Build.VERSION.SDK_INT >=21)
        {
              RemoteViews expandedView =
                      new RemoteViews(getPackageName(), R.layout.notification_expanded);
              notification.bigContentView= expandedView;
        }
        //END_CUSTOM_NOTI

        //SHOW notification with notification manager
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0,notification);





    }


}
