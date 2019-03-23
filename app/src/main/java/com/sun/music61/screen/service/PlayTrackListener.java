package com.sun.music61.screen.service;

import com.sun.music61.data.model.Track;

public interface PlayTrackListener {
    void onState(int state);
    void onTrackChanged(Track track);
}
