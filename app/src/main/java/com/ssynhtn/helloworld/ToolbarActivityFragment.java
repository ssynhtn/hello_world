package com.ssynhtn.helloworld;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssynhtn.helloworld.debug.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ToolbarActivityFragment extends Fragment {

    public ToolbarActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toolbar, container, false);
    }
}
