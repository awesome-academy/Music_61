package com.sun.music61.data.source;

import android.support.annotation.NonNull;

import com.sun.music61.data.model.Track;

import java.util.List;

/**
 * Main entry point for accessing tracks data.
 * </p>
 */
public interface TracksDataSource {

    List<Track> getTracks();

    Track getTrack(@NonNull String trackId);

    void refreshTracks();

}
