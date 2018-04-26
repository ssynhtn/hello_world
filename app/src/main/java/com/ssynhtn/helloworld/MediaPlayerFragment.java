package com.ssynhtn.helloworld;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huangtongnao on 2018/4/23.
 */

public class MediaPlayerFragment extends Fragment {

    private static final String TAG = MediaPlayerFragment.class.getSimpleName();

    @BindView(R.id.seek_bar)
    SeekBar seek_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seek(seekBar.getProgress());
            }
        });
    }


    private void seek(int progress) {
        if (player != null) {
            if (!player.isPlaying()) {
                player.start();
            }
            player.seekTo(progress);
        }

    }

    @OnClick({R.id.btn_play})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_play: {
                playMusic();
                break;
            }
        }
    }

    private MediaPlayer player;
    private int bufferPercent = -1;
    private void playMusic() {
        if (player == null) {
//            File sdcard = Environment.getExternalStorageDirectory();
//            File music = new File(sdcard, "netease/cloudmusic/Music/Brad Breeck - Made Me Realize (Gravity Falls Main Title Theme ) ［from ”Gravity Falls”］.mp3");
            player = new MediaPlayer();
        }

        if (!player.isPlaying()) {
            try {
//                player.setDataSource(music.getCanonicalPath());
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        seek_bar.setMax(player.getDuration());
                        player.start();
                    }
                });
                player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        if (percent != bufferPercent) {
                            bufferPercent = percent;
                            Log.d(TAG, "buffering " + bufferPercent);
                        }
                    }
                });
                player.setDataSource("http://47.97.116.114:8083/patient/play/init&userid=30823&type=music&id=2&timestamp=1524454017811&sign=8758edc03889b3a5053e4a0a0ee823fc");
                player.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        pauseMusic();
    }

    private void pauseMusic() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (player != null) {
            player.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
//            player.stop();
            player.release();
        }
    }
}
