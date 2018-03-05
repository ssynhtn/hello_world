package com.ssynhtn.helloworld;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FinishingVsDestroyedActivity extends AppCompatActivity {

    private static final String TAG = FinishingVsDestroyedActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishing_vs_destroyed);

        new SimpleTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 如果onPause是因为退出activity导致的, 那么isFinishing == true, isDestroyed == false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Log.d(TAG, "Pause. isFinishing " + isFinishing() + ", isDestroyed " + isDestroyed());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Log.d(TAG, "Destroy isFinishing " + isFinishing() + ", isDestroyed " + isDestroyed());
        }
    }

    class SimpleTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    publishProgress();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.d(TAG, "progress. isFinishing " + isFinishing() + ", isDestroyed " + isDestroyed());
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            // 在activity结束后, isFinishing一直都是true, 所以这个api的名字不是很合适
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Log.d(TAG, "post. isFinishing " + isFinishing() + ", isDestroyed " + isDestroyed());
            }
        }
    }

}
