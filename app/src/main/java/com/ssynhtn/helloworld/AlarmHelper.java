package com.ssynhtn.helloworld;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by huangtongnao on 2017/11/24.
 */

public class AlarmHelper {
    public static void addAlarm(Context context, long delay) {
        Intent intent = new Intent(AlarmReceiver.ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long time = Calendar.getInstance().getTimeInMillis() + delay;
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);
        }
    }
}
