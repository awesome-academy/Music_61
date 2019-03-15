package com.sun.music61.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Function;
import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.TracksDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class TracksLocalDataSource implements TracksDataSource {

    @Nullable
    private static TracksLocalDataSource INSTANCE;

    @NonNull
    private Function<Cursor, Track> mTrackMapperFunction;

    // Prevent direct instantiation
    private TracksLocalDataSource(@NonNull Context context) {
        checkNotNull(context, "context cannot be null");
    }

    public static TracksLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TracksLocalDataSource(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
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
