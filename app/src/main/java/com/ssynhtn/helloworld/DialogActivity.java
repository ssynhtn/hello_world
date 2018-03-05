package com.ssynhtn.helloworld;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_dialog)
    public void showDialog(View view) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(editText.getText().toString())
                .create();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < 10; j++) {
                            sb.append(editText.getText().toString());
                        }
                        dialog.setMessage(sb.toString());
                    }
                });
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }
}
