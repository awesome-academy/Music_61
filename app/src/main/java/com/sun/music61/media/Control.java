package com.sun.music61.media;

import com.sun.music61.data.model.Track;

public interface Control {
    void create(Track track);
    void start();
    void change(Track track);
    void pause();
    void stop();
    void release();
    void reset();
    void seek(int milliseconds);
    long getDuration();
    long getCurrentDuration();
    void setState(@State int state);
    @State
    int getState();
}
