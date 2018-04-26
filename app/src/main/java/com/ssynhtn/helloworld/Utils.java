package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangtongnao on 2018/4/17.
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static List<ComponentName> readActivities(Context context) {
        Set<String> launcherActivities = new HashSet<>();
        Intent launcherIntent = new Intent();
        launcherIntent.setAction(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(launcherIntent, 0);
        for (ResolveInfo info : resolveInfos) {
            if (info.activityInfo.taskAffinity == null) {
                Log.d(TAG, "no affinity " + info.activityInfo.name);
                // no affinity com.google.android.googlequicksearchbox.VoiceSearchActivity
                // no affinity com.google.android.googlequicksearchbox.SearchActivity
                continue;
            }
            if (info.activityInfo.taskAffinity.equals(context.getPackageName())) {
                launcherActivities.add(info.activityInfo.name);
            }
        }


        List<ComponentName> res = new ArrayList<>();
        try {
            ActivityInfo[] activityInfos = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo info : activityInfos) {
                if (launcherActivities.contains(info.name)) continue;
                if (!info.taskAffinity.equals(context.getPackageName())) continue;

                res.add(new ComponentName(context, info.name));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        return res;
    }
}
