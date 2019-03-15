package com.sun.music61.data.source.remote;

import android.support.annotation.NonNull;

import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.TracksDataSource;

import java.util.List;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TracksRemoteDataSource implements TracksDataSource {

    private static TracksRemoteDataSource INSTANCE;

    public static TracksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TracksRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TracksRemoteDataSource() {
    }

    @Override
    public List<Track> getTracks() {
        // Code late
        return null;
    }

    @Override
    public Track getTrack(@NonNull String trackId) {
        // Code late
        return null;
    }

    @Override
    public void refreshTracks() {
        // Not required because the {@link TracksRepository} handles the logic of refreshing the
        // tracks from all the available data sources.
    }
}
