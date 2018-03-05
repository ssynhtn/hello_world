package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MyRemoteService extends Service {

    private class ReceiverHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = (Bundle) msg.obj;
            String text = bundle.getString("key");
            Toast.makeText(MyRemoteService.this, "service received " + msg.what + ", " + text, Toast.LENGTH_SHORT).show();
            Message reply = Message.obtain();
            reply.what = 200;
            Bundle bd = new Bundle();
            bd.putString("key", "aha!");
            reply.obj = bd;
            try {
                msg.replyTo.send(reply);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    private Messenger messenger = new Messenger(new ReceiverHandler());

    public MyRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


}
