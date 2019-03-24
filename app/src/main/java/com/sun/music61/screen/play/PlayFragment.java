package com.sun.music61.screen.play;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.sun.music61.R;
import com.sun.music61.data.model.Track;
import com.sun.music61.media.State;
import com.sun.music61.screen.MainActivity;
import com.sun.music61.screen.service.PlayTrackListener;
import com.sun.music61.screen.service.PlayTrackService;
import com.sun.music61.util.CommonUtils;
import com.sun.music61.util.RepositoryInstance;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Objects;
import me.tankery.lib.circularseekbar.CircularSeekBar;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlayFragment extends Fragment implements
        PlayContract.View, PlayTrackListener, CircularSeekBar.OnCircularSeekBarChangeListener {

    private static final String ARGUMENT_TRACK = "ARGUMENT_TRACK";
    private static final int DEFAULT_PROGRESS = 0;
    private static final long TIME_DELAY = 1000;

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
    private Animation mAnimation;
    private PlayContract.Presenter mPresenter;
    private PlayTrackService mService;
    private Handler mHandlerSyncTime;

    public static PlayFragment newInstance(Track track) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.play_fragment, container, false);
        initView(rootView);
        onListenerEvent();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new PlayPresenter(RepositoryInstance.getInstanceTrackRepository(), this);
        mService = ((MainActivity) Objects.requireNonNull(getActivity())).getService();
        mService.addListeners(this);
        fetchDataTrack(mService.getCurrentTrack());
        handlerSyncTime();
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
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_image_song);
    }

    private void fetchDataTrack(Track track) {
        mToolbar.setTitle(track.getTitle());
        mToolbar.setSubtitle(track.getUser().getUsername());
        mSeekBarProccess.setProgress(DEFAULT_PROGRESS);
        CommonUtils.loadImageFromUrl(mImageBackground, track.getArtworkUrl(), CommonUtils.T500);
        CommonUtils.loadImageFromUrl(mImageSong, track.getArtworkUrl(), CommonUtils.T300);
        if (mService.getState() == State.PAUSE) {
            mImagePlay.setImageResource(R.drawable.ic_play_48dp);
            mImageSong.clearAnimation();
        } else {
            mImageSong.startAnimation(mAnimation);
            mImagePlay.setImageResource(R.drawable.ic_pause_48dp);
        }
    }

    private void onListenerEvent() {
        mButtonPlay.setOnClickListener(view -> play());
        mSeekBarProccess.setOnSeekBarChangeListener(this);
    }

    private void handlerSyncTime() {
        mHandlerSyncTime = new Handler();
        mHandlerSyncTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mService.getState() == State.PLAY) {
                    long totalTime = mService.getDuration();
                    long currentTime = mService.getCurrentDuration();
                    mSeekBarProccess.setProgress(CommonUtils.progressPercentage(currentTime, totalTime));
                    mTextDuration.setText(CommonUtils.convertTimeInMilisToString(currentTime));
                }
                mHandlerSyncTime.postDelayed(this, TIME_DELAY);
            }
        }, TIME_DELAY);
    }

    @Override
    public void setPresenter(PlayContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showErrors(String message) {

    }

    private void play() {
        if (mService.getState() == State.PAUSE) {
            mService.startTrack();
            mImageSong.startAnimation(mAnimation);
            mImagePlay.setImageResource(R.drawable.ic_pause_48dp);
        } else {
            mService.pauseTrack();
            mImageSong.clearAnimation();
            mImagePlay.setImageResource(R.drawable.ic_play_48dp);
        }
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, float progress,
            boolean fromUser) {
        if (fromUser) {
            mService.seek(CommonUtils.progressToTimer(progress, mService.getDuration()));
            mTextDuration.setText(CommonUtils.convertTimeInMilisToString((long) progress));
        }
    }

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {
        mTextDuration.setText(CommonUtils.convertTimeInMilisToString((long) seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {
        mTextDuration.setText(CommonUtils.convertTimeInMilisToString((long) seekBar.getProgress()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Objects.requireNonNull(getActivity())
                .getMenuInflater().inflate(R.menu.option_menu_play, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionPlayList:
                TracksModalFragment.showInstance(getChildFragmentManager());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mService.removeListener(this);
    }

    @Override
    public void onState(int state) {
        if (mService.getState() == State.PAUSE) {
            mImagePlay.setImageResource(R.drawable.ic_play_48dp);
            mImageSong.clearAnimation();
        } else {
            mImagePlay.setImageResource(R.drawable.ic_pause_48dp);
            mImageSong.startAnimation(mAnimation);
        }
    }

    @Override
    public void onTrackChanged(Track track) {
        fetchDataTrack(track);
    }
}
