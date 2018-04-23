package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ShortLivedService extends Service {
    public ShortLivedService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
