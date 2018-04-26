package com.ssynhtn.helloworld;

import android.os.Bundle;

public class TestDragActivity extends DragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DragLayout dragLayout = findViewById(R.id.dragLayout);
        dragLayout.setDragVertical(true);
        dragLayout.setDragHorizontal(true);
    }

    @Override
    protected String getOption() {
        return "";
    }
}
