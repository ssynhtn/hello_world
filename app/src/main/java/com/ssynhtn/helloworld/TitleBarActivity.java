package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TitleBarActivity extends AppCompatActivity {
//
//    @BindView(R.id.left)
//    View left;
//    @BindView(R.id.right)
//    View right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_bar);

//        ButterKnife.bind(this);
//
//        EditText editText = findViewById(R.id.edit);
//        final TextView textView = findViewById(R.id.center);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                textView.setText(s.toString());
//            }
//        });

    }

//    @OnClick({R.id.btn_left, R.id.btn_right})
//    public void onClickView(View view) {
//        switch (view.getId()) {
//            case R.id.btn_left: {
//                left.setVisibility(left.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                break;
//            }
//            case R.id.btn_right: {
//                right.setVisibility(right.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                break;
//            }
//        }
//    }
}
