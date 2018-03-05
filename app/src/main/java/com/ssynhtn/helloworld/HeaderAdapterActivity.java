package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderAdapterActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_adapter);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final HeaderAdapter adapter = new HeaderAdapter(20);
        final ScanDeviceView scanDeviceView = (ScanDeviceView) LayoutInflater.from(this).inflate(R.layout.header, recyclerView, false);
        adapter.addHeaderView(scanDeviceView);
//        final View footer = LayoutInflater.from(this).inflate(R.layout.unbind_device_view, recyclerView, false);
//        final ProgressButton unbindView = footer.findViewById(R.id.pb);
//        unbindView.setInProgress(false, true);
//        unbindView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                unbindView.setInProgress(true, false);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        footer.setVisibility(View.GONE);
//                        unbindView.setInProgress(false, false);
//                        adapter.notifyItemChanged(adapter.getFooterViewPosition(footer));
//                    }
//                }, 2000);
//            }
//        });
//        adapter.addFooterView(footer);
        final Button footer = new Button(this);
        footer.setText("footer");
//        final SomeAdapter adapter = new SomeAdapter(Arrays.asList("h fdlsfj fdalkf sf ".split(" ")), scanDeviceView, footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.hideFooterView(footer);
//                        footer.setVisibility(View.GONE);
//                        adapter.notifyItemChanged(adapter.getItemCount() - 2);
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.notifyItemChanged(adapter.getItemCount() - 1);
//                            }
//                        }, 2000);
                    }
                }, 2000);
            }
        });
        adapter.addFooterView(footer);
        scanDeviceView.startAnimation();

        recyclerView.setAdapter(adapter);
    }
}
