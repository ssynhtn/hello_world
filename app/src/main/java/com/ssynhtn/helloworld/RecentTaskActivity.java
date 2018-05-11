package com.ssynhtn.helloworld;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ssynhtn.helloworld.view.SeekBarCompanionView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class RecentTaskActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = RecentTaskActivity.class.getSimpleName();
    private static final String CHANNEL_ID = "my_channel";

    @BindView(R.id.img)
    View imageView;

    @BindView(R.id.seek_bar)
    SeekBar seekBar;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.img_view)
    ImageView img_view;

    @BindView(R.id.img_view_two)
    ImageView img_view_two;

    @BindView(R.id.avd_progress)
    ProgressBar avd_progress;

    @BindViews({R.id.seek_bar_two, R.id.seek_bar_three})
    List<SeekBar> seekBars;

    @BindView(R.id.seek_bar_bottom)
    SeekBar seek_bar_bottom;
    @BindView(R.id.seekbarParent)
    SeekBarCompanionView seekbarCompanionView;

    @BindView(R.id.in_prog)
    ProgressBar in_prog;

    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_task);

        ButterKnife.bind(this);



//        Drawable avd = ContextCompat.getDrawable(this, R.drawable.avd);
        Drawable avd = ContextCompat.getDrawable(this, R.drawable.animation_test);
        avd_progress.setProgressDrawable(avd);

        in_prog.setIndeterminateDrawable(avd);

        if (img_view.getDrawable() instanceof Animatable) {
            ((Animatable) img_view.getDrawable()).start();
        }

        if (img_view_two.getDrawable() instanceof Animatable) {
            ((Animatable) img_view_two.getDrawable()).start();
        }

        LayerDrawable layerDrawable = (LayerDrawable) imageView.getBackground();
        Drawable drawable = layerDrawable.findDrawableByLayerId(R.id.layer_1);
        drawable.setLevel(10000);



        seekBars.get(0).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LayerDrawable layerDrawable = (LayerDrawable) imageView.getBackground();


                Drawable drawable = layerDrawable.findDrawableByLayerId(R.id.layer_2);
                drawable.setLevel(progress * 100);

                progressBar.setProgress(progress / 2);
                progressBar.setSecondaryProgress(progress);

                avd_progress.setProgress(progress);

                for (int i = 1; i < seekBars.size(); i++) {
                    seekBars.get(i).setProgress(progress);
                }

                RecentTaskActivity.this.seekBar.setProgress(progress);
                Toast.makeText(RecentTaskActivity.this, "progress " + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setSecondaryProgress((seekBar.getProgress() + seekBar.getMax()) / 2);
            }
        });

        seekbarCompanionView.setupWithSeekBar(seek_bar_bottom);

    }

    private void notifyIntent() {
        Intent intent = new Intent(this, RecentTaskActivity.class);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_circle_raster)
                .setContentTitle("hello")
                .setContentText("world")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "my_name";
            String description = "my_desc";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, mBuilder.build());
    }

    private void test() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> myTasks = activityManager.getAppTasks();
            for (int i = 0; i < myTasks.size(); i++) {
                logTask(i, myTasks.get(i));
            }
        }
    }

    private void logTask(int index, ActivityManager.AppTask appTask) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityManager.RecentTaskInfo info = appTask.getTaskInfo();
            Log.d(TAG, "base activity " + info.baseActivity + ", top " + info.topActivity + ", desc " + info.description + ", orig " + info.origActivity + ", index " + index);
        }


    }
}
