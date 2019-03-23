package com.sun.music61.screen.play;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.sun.music61.R;
import com.sun.music61.data.model.Track;
import com.sun.music61.media.MediaPlayerManager;
import com.sun.music61.media.State;
import com.sun.music61.util.CommonUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Objects;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class PlayFragment extends Fragment
        implements CircularSeekBar.OnCircularSeekBarChangeListener {

    private static final String ARGUMENT_TRACK = "ARGUMENT_TRACK";

    private Toolbar mToolbar;
    private KenBurnsView mImageBackground;
    private CircularSeekBar mSeekBarProccess;
    private CircleImageView mImageSong;
    private TextView mTextDuration;
    private ImageView mButtonSuffle;
    private ImageView mButtonPrev;
    private ImageView mButtonNext;
    private ImageView mButtonPlay;
    private ImageView mButtonRepeat;
    private ImageView mImagePlay;
    private Track mTrack;
    private MediaPlayerManager mPlayerManager;

    public static PlayFragment newInstance(Track track) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Get Data From Activity
        mTrack = Objects.requireNonNull(getArguments()).getParcelable(ARGUMENT_TRACK);
        View rootView = inflater.inflate(R.layout.play_fragment, container, false);
        initView(rootView);
        onListenerEvent();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchDataTrack(mTrack);
        // Playing
        onPlayMusic();
    }

    private void onPlayMusic() {
        mPlayerManager = MediaPlayerManager.getInstance(getContext());
        mPlayerManager.create(mTrack);
        mPlayerManager.start();
    }

    private void initView(View rootView) {
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mImageBackground = rootView.findViewById(R.id.imageBackground);
        mSeekBarProccess = rootView.findViewById(R.id.seekBarProcess);
        mImageSong = rootView.findViewById(R.id.imageSongCircle);
        mTextDuration = rootView.findViewById(R.id.textDuration);
        mButtonSuffle = rootView.findViewById(R.id.buttonShuffle);
        mButtonPrev = rootView.findViewById(R.id.buttonPrev);
        mButtonNext = rootView.findViewById(R.id.buttonNext);
        mButtonPlay = rootView.findViewById(R.id.buttonPlay);
        mButtonRepeat = rootView.findViewById(R.id.buttonRepeat);
        mImagePlay = rootView.findViewById(R.id.imagePlay);
    }

    private void fetchDataTrack(Track track) {
        mToolbar.setTitle(mTrack.getTitle());
        mToolbar.setSubtitle(mTrack.getUser().getUsername());
        CommonUtils.loadImageFromUrl(mImageBackground, track.getArtworkUrl(), CommonUtils.T500);
        CommonUtils.loadImageFromUrl(mImageSong, track.getArtworkUrl(), CommonUtils.T300);
    }

    private void onListenerEvent() {
        mButtonPlay.setOnClickListener(view -> play());
        mSeekBarProccess.setOnSeekBarChangeListener(this);
    }

    private void play() {
        if (mPlayerManager.getState() == State.PLAY) {
            mPlayerManager.pause();
            //Changing button image to play Button
            mImagePlay.setImageResource(R.drawable.ic_play_48dp);
        } else {
            //Resume song
            mPlayerManager.start();
            mImagePlay.setImageResource(R.drawable.ic_pause_48dp);
        }
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, float progress,
            boolean fromUser) {
        // Code late
    }

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {
        // Code late
    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {
        // Code late
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerManager.release();
    }
}
