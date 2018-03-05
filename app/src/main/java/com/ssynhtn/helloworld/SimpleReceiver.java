package com.ssynhtn.helloworld;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class SimpleReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        double num = intent.getDoubleExtra("random", 0);
        byte[] bytes = intent.getByteArrayExtra("model");
        NotificationModel model = ParcelableUtil.unMarshall(bytes, NotificationModel.CREATOR);
        boolean extrasNull = intent.getExtras() == null;
        Toast.makeText(context, "receiver " + num + ", model " + model + " extrasNull ? " + extrasNull, Toast.LENGTH_SHORT).show();

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
//        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(alarmIntent);


        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("hello")
                .setContentText("hello")
                .setAutoCancel(true)
                //				.setNumber(number)//显示数量
                .setTicker("hello")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis() + 1000)//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                //				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);




        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());
    }
}
