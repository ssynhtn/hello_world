package com.ssynhtn.helloworld;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.MimeTypes;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExoPlayerActivity extends AppCompatActivity {

    private final String USER_AGENT = "hello_world";
    static final String VIDEO_URL = "https://redirector.googlevideo.com/videoplayback?requiressl=yes&fexp=23724337&lmt=1484811218767399&fvip=4&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&dur=106.138&key=yt6&source=youtube&ei=hJreWok41JKAB4vgodAK&ratebypass=yes&mn=sn-4g5e6nez%2Csn-4g5edns6&id=o-AN7vipidiByXNsV2skPrnbC1z9TRjqdZNHyfo1BmtlXB&itag=22&mm=31%2C29&pl=24&c=WEB&mime=video%2Fmp4&ip=5.9.21.24&expire=1524559588&signature=A5DF1706A08EEB47E10159CE1632B1341C05506E.DD740C154185A960030D083DFAA43BCA67FA3CCE&ms=au%2Crdu&ipbits=0&initcwndbps=5802500&mv=m&mt=1524537826&title=Cute%20otters%20intimately%20filmed%20by%20spy%20camera%20-%20Spy%20in%20the%20Wild%20Episode%202%20Preview%20-%20BBC%20One";
    static final String VIDEO_FILE = "videoplayback.3gp";

    @BindView(R.id.player_view)
    PlayerView player_view;
    private ExoPlayer player;

    private boolean playWhenReady;
    private long currentPosition;
    private int currentWindowIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        initPlayer();
    }

    private void initPlayer() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            player_view.setPlayer(player);


            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindowIndex, currentPosition);

            player.prepare(createLocalMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.jazz_in_paris)));
        }
    }


    private MediaSource createLocalMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, USER_AGENT);
        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private MediaSource createHttpMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(USER_AGENT);
        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private MediaSource createSubtitleMediaSource() {
        Uri srtUri = RawResourceDataSource.buildRawResourceUri(R.raw.south_park_cn_en);
        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, Format.NO_VALUE, null);
        return new SingleSampleMediaSource.Factory(new DefaultDataSourceFactory(this, USER_AGENT)).createMediaSource(srtUri, textFormat, C.TIME_UNSET);
    }

    private MediaSource createMediaSource() {
        return new LoopingMediaSource(new ConcatenatingMediaSource(createLocalMediaSource(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), VIDEO_FILE))), new MergingMediaSource(createHttpMediaSource(Uri.parse(VIDEO_URL)), createSubtitleMediaSource()), createLocalMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.jazz_in_paris))), 2);
    }

    @Override
    protected void onPause() {
        super.onPause();

        releasePlayer();
    }

    private void releasePlayer() {
        currentWindowIndex = player.getCurrentWindowIndex();
        currentPosition = player.getCurrentPosition();
        playWhenReady = player.getPlayWhenReady();
        player.release();
        player = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("playWhenReady", playWhenReady);
        outState.putInt("currentWindowIndex", currentWindowIndex);
        outState.putLong("currentPosition", currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        playWhenReady = savedInstanceState.getBoolean("playWhenReady");
        currentWindowIndex = savedInstanceState.getInt("currentWindowIndex");
        currentPosition = savedInstanceState.getLong("currentPosition");
    }

    @OnClick({R.id.btn_play, R.id.btn_test})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_play: {
                playOrPause();
                break;
            }
            case R.id.btn_test: {
                player.seekTo(player.getCurrentPosition() + 5000);
                break;
            }
        }
    }

    private void playOrPause() {
        if (player != null) {
            player.setPlayWhenReady(!player.getPlayWhenReady());
        }
    }

}
