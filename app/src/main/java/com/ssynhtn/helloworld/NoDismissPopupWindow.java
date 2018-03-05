package com.ssynhtn.helloworld;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by huangtongnao on 2017/12/1.
 */

public class NoDismissPopupWindow extends PopupWindow {
    @Override
    public void setContentView(View contentView) {
        if(contentView != null) {
            super.setContentView(contentView);
            contentView.setFocusable(true);   //
            contentView.setFocusableInTouchMode(true);   //
            contentView.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            dismiss();
                            return true;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private Drawable mBackgroundDrawable;
    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        mBackgroundDrawable = background;
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if(touchable) {
            if(mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

}
