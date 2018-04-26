package com.ssynhtn.helloworld;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Flavien Laurent (flavienlaurent.com) on 23/08/13.
 */
public abstract class DragActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        DragLayout dragLayout = (DragLayout) findViewById(R.id.dragLayout);

        switch (getOption()) {
            case "horizontal": dragLayout.setDragHorizontal(true); break;
            case "vertical": dragLayout.setDragVertical(true); break;
            case "edge":
                dragLayout.setDragEdge(true);
                break;
            case "capture":
                dragLayout.setDragCapture(true);
                break;
        }
    }

    protected abstract String getOption();
}
