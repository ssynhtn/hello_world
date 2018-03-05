package com.ssynhtn.helloworld;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobActivity extends AppCompatActivity {

    private static final String TAG = JobActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_job)
    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_job: {
                job(1, 10);
                break;
            }
        }
    }

    private void job(final int index, final int limit) {
        if (index >= limit) return;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobInfo.Builder builder = new JobInfo.Builder(Constants.JOB_ID_SOME, new ComponentName(this, MyJobService.class));
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//            builder.setOverrideDeadline(TimeUnit.MINUTES.toMillis(30));
            PersistableBundle extras = new PersistableBundle();
            extras.putString("hello", "world");
            extras.putInt("int", index);
            builder.setExtras(extras);

            final JobInfo jobInfo = builder.build();
            final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (jobScheduler != null) {
                Log.d(TAG, "schedule " + System.currentTimeMillis());
                jobScheduler.schedule(jobInfo);

                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        job(index + 1, limit);
                    }
                }, 1000);

            }

        }
    }
}
