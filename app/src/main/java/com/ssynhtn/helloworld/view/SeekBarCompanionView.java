package com.ssynhtn.helloworld.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.ssynhtn.helloworld.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtongnao on 2018/5/9.
 */

public class SeekBarCompanionView extends FrameLayout implements Observer<Boolean> {

    private SeekBar seekBar;
    private ProgressBar progressBar;

    private LiveData<Boolean> isPlayingLiveData;

    private int xOffset;
    private int yOffset;

    private boolean firstLayout = true;

    private WrapperSeekBarListener wrapperSeekBarListener = new WrapperSeekBarListener();

    public SeekBarCompanionView(@NonNull Context context) {
        this(context, null);
    }

    public void setIsPlayingLiveData(LifecycleOwner lifecycleOwner, LiveData<Boolean> liveData) {
        if (liveData == null) return;

        if (this.isPlayingLiveData != null) {
            isPlayingLiveData.removeObserver(this);
        }

        this.isPlayingLiveData = liveData;
        liveData.observe(lifecycleOwner, this);
    }



    public SeekBarCompanionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        LayoutInflater.from(context).inflate(R.layout.progress_bar, this, true);

        progressBar = findViewById(R.id.progress_bar);
    }

    public void setupWithSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(wrapperSeekBarListener);
        wrapperSeekBarListener.addOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgressBarPosition(seekBar, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (firstLayout) {
            updateProgressBarPosition(seekBar, seekBar.getProgress());
            firstLayout = false;
        }
    }



    private void updateProgressBarPosition(SeekBar seekBar, int progress) {
        int left = seekBar.getLeft();
        int paddingLeft = seekBar.getPaddingLeft();
        int paddingRight = seekBar.getPaddingRight();
        int seekBarWidth = seekBar.getWidth() - paddingLeft - paddingRight;

        int progressWidth = (int) (seekBarWidth * progress * 1.0f / seekBar.getMax());
        int x = left + paddingLeft + progressWidth - progressBar.getWidth() / 2 + xOffset;

        progressBar.setTranslationX(x);

    }


    public void addOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        wrapperSeekBarListener.addOnSeekBarChangeListener(onSeekBarChangeListener);
    }



    @Override
    public void onChanged(@Nullable Boolean isPlaying) {
        if (isPlaying != null) {
            progressBar.setVisibility(isPlaying ? VISIBLE : GONE);
        }
    }

    private static class WrapperSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        private List<SeekBar.OnSeekBarChangeListener> listeners = new ArrayList<>();

        public void addOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener) {
            listeners.add(listener);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            for (SeekBar.OnSeekBarChangeListener listener : listeners) {
                listener.onProgressChanged(seekBar, progress, fromUser);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            for (SeekBar.OnSeekBarChangeListener listener : listeners) {
                listener.onStartTrackingTouch(seekBar);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            for (SeekBar.OnSeekBarChangeListener listener : listeners) {
                listener.onStopTrackingTouch(seekBar);
            }
        }
    }

}
