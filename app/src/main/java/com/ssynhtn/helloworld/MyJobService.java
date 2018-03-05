package com.ssynhtn.helloworld;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();


    public MyJobService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "start job " + System.currentTimeMillis());

        if (jobParameters.isOverrideDeadlineExpired()) {
            // constraints might not met
        } else {
            // constraints met
        }

        if (jobParameters.getJobId() == Constants.JOB_ID_SOME) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String world = jobParameters.getExtras().getString("hello");
                    jobFinished(jobParameters, false);
                    Log.d(TAG, "finish job " + System.currentTimeMillis() + ", hello " + world + ", int " + jobParameters.getExtras().getInt("int"));

                }
            }, 3000);
            return true;

        } else {
            return false;
        }
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
