package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean isBind = false;
    private ServiceConnection serviceConnection;


    private Handler receiverHandler = new ReceiverHandler();
    private Messenger messengerReceiver = new Messenger(receiverHandler);
    private Messenger messengerSender;

    private class ReceiverHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = (Bundle) msg.obj;
            String text = bundle.getString("key");
            Toast.makeText(MainActivity.this, "client get what " + msg.what + ", text " + text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBind) {
                    disconnect();
                } else {
                    connect();
                }
            }
        });

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                isBind = true;
                Toast.makeText(MainActivity.this, "service bind", Toast.LENGTH_SHORT).show();
//                MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
//                MyService myService = myBinder.getService();
//                myService.hello();

                messengerSender = new Messenger(iBinder);
                Message msg = Message.obtain();
                msg.what = 100;
                Bundle bundle = new Bundle();
                bundle.putString("key", "hello from client");
                msg.obj = bundle;
                msg.replyTo = messengerReceiver;
                try {
                    messengerSender.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Toast.makeText(MainActivity.this, "service unbind", Toast.LENGTH_SHORT).show();
                isBind = false;
                messengerSender = null;
            }
        };
    }

    private void sendMessage() {
        int stuff = (int) (Math.random() * 100);
        String text = String.valueOf(stuff);

        Message message = Message.obtain();
        message.what = stuff;
        Bundle bundle = new Bundle();
        bundle.putString("key", text);
        message.obj = bundle;
        message.replyTo = messengerReceiver;
        try {
            messengerSender.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        if (isBind) return;
        Intent intent = new Intent(this, MyRemoteService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void disconnect() {
        if (isBind) {
            isBind = false;
            unbindService(serviceConnection);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
