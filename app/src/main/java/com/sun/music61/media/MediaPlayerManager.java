package com.sun.music61.media;

import android.net.Uri;
import com.sun.music61.data.model.Track;
import com.sun.music61.screen.service.PlayTrackService;
import com.sun.music61.util.CommonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediaPlayerManager extends BaseMedia {

    private PlayTrackService mService;
    private Track mCurrentTrack;
    private List<Track> mTracks;

    private static MediaPlayerManager sInstance;

    private MediaPlayerManager(PlayTrackService service) {
        super();
        mService = service;
        mTracks = new ArrayList<>();
    }

    public static MediaPlayerManager getInstance(PlayTrackService service) {
        if (sInstance == null)
            sInstance = new MediaPlayerManager(service);
        return sInstance;
    }

    @Override
    public  <T> void create(T obj) {
        Track track = (Track) obj;
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mService, Uri.parse(track.getStreamUrl() + CommonUtils.AUTHORIZED_SERVER));
        } catch (IOException e) {
            // Do nothing
        }
        mMediaPlayer.setOnErrorListener(mService);
        mMediaPlayer.setOnCompletionListener(mService);
        mMediaPlayer.setOnPreparedListener(mService);
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void start() {
        mMediaPlayer.start();
        setState(State.PLAY);
    }

    @Override
    public <T> void change(T obj) {
        mCurrentTrack = (Track) obj;
        create(obj);
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
        setState(State.PAUSE);
    }

    @Override
    public void previous() {
        change(getPreviousTrack());
    }

    @Override
    public void next() {
        if (getShuffle() == Shuffle.OFF) {
            change(getNextTrack());
        } else {
            change(getRandomTrack());
        }
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
        setState(State.PAUSE);
    }

    private Track getPreviousTrack() {
        int position = mTracks.indexOf(mCurrentTrack);
        if (position == 0) return mTracks.get(mTracks.size() - 1);
        return mTracks.get(position - 1);
    }

    private Track getNextTrack() {
        int position = mTracks.indexOf(mCurrentTrack);
        if (position == mTracks.size() - 1) return mTracks.get(0);
        return mTracks.get(position + 1);
    }

    private Track getRandomTrack() {
        Random random = new Random();
        return mTracks.get(random.nextInt(mTracks.size()));
    }

    public void setCurrentTrack(Track track) {
        mCurrentTrack = track;
    }

    public void setTracks(List<Track> tracks) {
        if (tracks != null) {
            mTracks.clear();
            mTracks.addAll(tracks);
        }
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void addTrack(Track track) {
        mTracks.add(track);
    }

    public void addTracks(List<Track> tracks) {
        mTracks.addAll(tracks);
    }

    public void removeTrack(Track track) {
        if (track != null) mTracks.remove(track);
    }

    public Track getCurrentTrack() {
        return mCurrentTrack;
    }

    public boolean isLastTracks(Track currentTrack) {
        return mTracks.indexOf(currentTrack) == mTracks.size() - 1;
    }
}
