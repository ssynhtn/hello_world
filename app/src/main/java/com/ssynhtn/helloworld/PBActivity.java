package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PBActivity extends AppCompatActivity {

    @BindView(R.id.pb)
    ProgressButton pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb);

        ButterKnife.bind(this);
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setInProgress(true, false);
                Toast.makeText(PBActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.restore)
    public void onClickView(View view) {
        pb.setInProgress(false, true);
    }
}
