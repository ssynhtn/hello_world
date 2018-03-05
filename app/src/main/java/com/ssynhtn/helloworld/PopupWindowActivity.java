package com.ssynhtn.helloworld;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;

public class PopupWindowActivity extends AppCompatActivity {

    @BindView(R.id.container)
    LinearLayout container;

    List<PopupWindow> windows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        ButterKnife.bind(this);

        for (int i = 0; i < container.getChildCount(); i++) {
            Button button = (Button) container.getChildAt(i);
            try {
                final int type = Integer.parseInt(button.getText().toString());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertPopup(ALERT_TYPE_SLEEP, "TYPE " + type, type);
                    }
                });
            } catch (Exception e) {

            }


            if (i == container.getChildCount() - 1) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (windows.isEmpty()) return;
                        PopupWindow popupWindow = windows.remove(windows.size() - 1);
                        popupWindow.dismiss();
                    }
                });
            }


        }
    }

    @OnClick({R.id.btn_2_windows, R.id.btn_2_windows_diff_type, R.id.btn_2_windows_diff_type_reverse})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_2_windows: {
                showAlertPopup(ALERT_TYPE_SLEEP, "first", Integer.MAX_VALUE);
                showAlertPopup(ALERT_TYPE_SLEEP, "second", Integer.MAX_VALUE);
                break;
            }

            case R.id.btn_2_windows_diff_type: {
                int[] types = {
                        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                        TYPE_APPLICATION_MEDIA,
                        TYPE_APPLICATION_SUB_PANEL,
                        TYPE_APPLICATION_ATTACHED_DIALOG,
                };

                for (int i = 0; i < types.length; i++) {
                    showAlertPopup(ALERT_TYPE_SLEEP, i + " TYPE " + types[i], types[i]);
                }
//                showAlertPopup(ALERT_TYPE_SLEEP, "1 TYPE " + WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
//                showAlertPopup(ALERT_TYPE_SLEEP, "2 type " + TYPE_APPLICATION_SUB_PANEL, TYPE_APPLICATION_SUB_PANEL);
//                showAlertPopup(ALERT_TYPE_SLEEP, "3 type " + (WindowManager.LayoutParams.LAST_SUB_WINDOW - 2), WindowManager.LayoutParams.LAST_SUB_WINDOW - 2);
//                showAlertPopup(ALERT_TYPE_SLEEP, "4 type " + (WindowManager.LayoutParams.LAST_SUB_WINDOW - 1), WindowManager.LayoutParams.LAST_SUB_WINDOW - 1);
//                showAlertPopup(ALERT_TYPE_SLEEP, "5 type " + WindowManager.LayoutParams.LAST_SUB_WINDOW, WindowManager.LayoutParams.LAST_SUB_WINDOW);
                break;
            }

            case R.id.btn_2_windows_diff_type_reverse: {
                int[] types = {
                        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                        TYPE_APPLICATION_MEDIA,
                        TYPE_APPLICATION_SUB_PANEL,
                        TYPE_APPLICATION_ATTACHED_DIALOG,
                };

                for (int i = 0; i < types.length; i++) {
                    showAlertPopup(ALERT_TYPE_SLEEP, i + " TYPE " + types[types.length - 1 - i], types[types.length - 1 - i]);
                }
//                showAlertPopup(ALERT_TYPE_SLEEP, "1 type " + WindowManager.LayoutParams.LAST_SUB_WINDOW, WindowManager.LayoutParams.LAST_SUB_WINDOW);
//                showAlertPopup(ALERT_TYPE_SLEEP, "2 type " + (WindowManager.LayoutParams.LAST_SUB_WINDOW - 1), WindowManager.LayoutParams.LAST_SUB_WINDOW - 1);
//                showAlertPopup(ALERT_TYPE_SLEEP, "3 type " + (WindowManager.LayoutParams.LAST_SUB_WINDOW - 2), WindowManager.LayoutParams.LAST_SUB_WINDOW - 2);
//                showAlertPopup(ALERT_TYPE_SLEEP, "4 type " + TYPE_APPLICATION_SUB_PANEL, TYPE_APPLICATION_SUB_PANEL);
//                showAlertPopup(ALERT_TYPE_SLEEP, "5 type " + WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
                break;
            }

            default: break;
        }
    }

    private static final String ALERT_TYPE_SLEEP = "sleep";
    private static final String ALERT_TYPE_EMOTIONAL = "emotional";
    private void showAlertPopup(final String alert, String alertContent, int type) {
        if (TextUtils.isEmpty(alert)) return;
        String buttonText;
        switch (alert) {
            case ALERT_TYPE_SLEEP: {
                buttonText = "知道了";
                break;
            }
            case ALERT_TYPE_EMOTIONAL: {
                buttonText = "点击评测";
                break;
            }

            default: return;
        }

        final PopupWindow popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window_main_fragment_alert, null);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        Button button = (Button) view.findViewById(R.id.button);
        textView.setText(alertContent);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (alert) {
                    case ALERT_TYPE_SLEEP: {
                        popupWindow.dismiss();
                        break;
                    }
                    case ALERT_TYPE_EMOTIONAL: {
                        popupWindow.dismiss();
                        break;
                    }
                    default: break;
                }
            }
        });

        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(getResources().getDimensionPixelSize(R.dimen.my280dp));
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(false);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.MainAlertPopup);
        windows.add(popupWindow);

        if (type != Integer.MAX_VALUE) {
            PopupWindowCompat.setWindowLayoutType(popupWindow, type);
        }
//        popupWindow.setAnimationStyle(R.style.MainAlertPopup);
//        PopupWindowCompat.setWindowLayoutType(popupWindow, 0);

        popupWindow.showAtLocation(container, Gravity.CENTER, 0, 0);

    }
}
