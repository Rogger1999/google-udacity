package com.tiredpapi.baking.baker.fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tiredpapi.baking.baker.R;
import com.tiredpapi.baking.baker.utils.Constants;

public class DetailFragment extends Fragment {
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer mExoPlayer;

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_fragment, container, false);
        TextView textViewDescription = view.findViewById(R.id.detail_fragment_tv);
        TextView textViewVideo = view.findViewById(R.id.detail_fragment_video_tv);

        playerView = view.findViewById(R.id.video_view);


        if(Constants.currentStep != -1 ) {
            textViewDescription.setText(Constants.jsonUtils.getInstructions());
            Log.i(Constants.TAG, "Delka: " + Constants.jsonUtils.getVideo().length());
            if (Constants.jsonUtils.getVideo().length() == 0) {
                textViewVideo.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
                textViewVideo.setText("NO URL");
            }
            else {
                playerView.setVisibility(View.VISIBLE);
                textViewVideo.setVisibility(View.INVISIBLE);
                initializePlayer(Uri.parse(Constants.jsonUtils.getVideo()));
            }

        }

        return view;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "BakerApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Constants.jsonUtils.getVideo().length() !=0)
            releasePlayer();
    }

}
