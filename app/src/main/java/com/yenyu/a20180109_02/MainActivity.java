package com.yenyu.a20180109_02;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity {

    String idLove="LOVE"; //用來分別不同的通知頻道
    NotificationChannel channelLove;
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //從系統service取出NOTIFICATION_SERVICE 通知管理

        if(Build.VERSION.SDK_INT >=26) //android8以上才執行
        {
            regChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    //只對android8做以下這段程式
    private void regChannel()
    {
        channelLove = new NotificationChannel(
                idLove,"Channel Love",
                NotificationManager.IMPORTANCE_HIGH);
        channelLove.setDescription("最重要的人"); //描述，不一定要填寫
        channelLove.enableLights(true);
        channelLove.enableVibration(true);
        nm.createNotificationChannel(channelLove);
        //建立一個通知頻道();
    }
    @TargetApi(Build.VERSION_CODES.O) //針對android oreo寫的程式
    @SuppressWarnings("deprecation") //忽略舊的程式造成的錯誤
    public void click1(View v) //點擊後跳處推撥通知
    {
        Notification.Builder builder;
        if(Build.VERSION.SDK_INT>= 26) //如果符合android8以上 有channel
        {
            builder =new Notification.Builder(MainActivity.this, idLove);
        }
        else // 不到8的版本沒有channel這個參數
        {
            builder =new Notification.Builder(MainActivity.this);
        }

        Intent it = new Intent(MainActivity.this,InfoActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP|
        Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 123,
                it, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("測試");
        builder.setContentText("這是內容");
        if(Build.VERSION.SDK_INT>=26) //android8跟以前的版本放的圖案大小不同，使用上需注意
        {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //小圖案
        }
        else
        {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setAutoCancel(true); //點了後自動消失
        builder.setContentIntent(pi);

        Notification notify = builder.build();
        nm.notify(1, notify);

    }
}
