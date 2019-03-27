package com.sun.music61.screen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.sun.music61.R;
import com.sun.music61.data.model.Track;
import com.sun.music61.media.MediaPlayerManager;
import java.util.ArrayList;
import java.util.List;

public class PlayTrackService extends Service implements
        BasePlayTrackService,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener  {

    private IBinder mBinder;
    private MediaPlayerManager mPlayerManager;
    private List<PlayTrackListener> mListeners;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new TrackBinder();
        mPlayerManager = MediaPlayerManager.getInstance(this);
        mListeners = new ArrayList<>();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayTrackService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    private void handleIntent(Intent intent) {
        if (intent.getAction() == null) return;
        // Code late
    }

    private void notifyStateChange() {
        for (PlayTrackListener listener : mListeners) {
            listener.onState(getState());
        }
    }

    private void notifyTrackChange() {
        notifyStateChange();
        for (PlayTrackListener listener : mListeners) {
            listener.onTrackChanged(getCurrentTrack());
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Toast.makeText(this, getString(R.string.notify_play_track_error), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void createTrack(Track track) {
        mPlayerManager.create(track);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Handle notification late
        startTrack();
    }

    @Override
    public void startTrack() {
        mPlayerManager.start();
        notifyStateChange();
        // Notification late
    }

    @Override
    public void changeTrack(Track track) {
        // Code late
        mPlayerManager.change(track);
        notifyTrackChange();
    }

    @Override
    public void pauseTrack() {
        mPlayerManager.pause();
        // Notification late
        notifyStateChange();
    }

    @Override
    public void stopTrack() {
        mPlayerManager.stop();
    }

    @Override
    public void seek(int milliseconds) {
        mPlayerManager.seek(milliseconds);
    }

    @Override
    public long getDuration() {
        return mPlayerManager.getDuration();
    }

    @Override
    public long getCurrentDuration() {
        return mPlayerManager.getCurrentDuration();
    }

    @Override
    public int getState() {
        return mPlayerManager.getState();
    }

    public Track getCurrentTrack() {
        return mPlayerManager.getCurrentTrack();
    }

    public void addListeners(PlayTrackListener listener) {
        mListeners.add(listener);
    }

    public void removeAllListeners() {
        mListeners.clear();
    }

    public void removeListener(PlayTrackListener listener) {
        mListeners.remove(listener);
    }

    public class TrackBinder extends Binder {
        public PlayTrackService getService() {
            return PlayTrackService.this;
        }
    }
}
