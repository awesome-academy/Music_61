package com.sun.music61.screen.service;

import com.sun.music61.data.model.Track;

public interface BasePlayTrackService {
    void createTrack(Track track);
    void startTrack();
    void changeTrack(Track track);
    void pauseTrack();
    void stopTrack();
    void seek(int milliseconds);
    long getDuration();
    long getCurrentDuration();
    int getState();
}
