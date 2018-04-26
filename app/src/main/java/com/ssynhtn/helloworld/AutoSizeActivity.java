package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class AutoSizeActivity extends AppCompatActivity {

    private static final String TAG = AutoSizeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_size);

        EditText editText = findViewById(R.id.edit_text);
        final TextView textView = findViewById(R.id.text_view);
        final EditText editTextTwo = findViewById(R.id.edit_two);
        Log.d(TAG, "textView " + textView);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(s.toString());
                editTextTwo.setText(s.toString());
//                textView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        editTextTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize());
//                    }
//                });
            }
        });


    }
}
