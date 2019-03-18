package com.sun.music61.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TracksRepository implements TracksDataSource {

    @Nullable
    private static TracksRepository sInstance;

    @NonNull
    private final TracksDataSource mTracksRemoteDataSource;

    @NonNull
    private final TracksDataSource mTracksLocalDataSource;

    private TracksRepository(@NonNull TracksDataSource tracksRemoteDataSource, @NonNull TracksDataSource tracksLocalDataSource) {
        mTracksRemoteDataSource = tracksRemoteDataSource;
        mTracksLocalDataSource = tracksLocalDataSource;
    }

    public static TracksRepository getInstance(@NonNull TracksDataSource tracksRemoteDataSource, @NonNull TracksDataSource tracksLocalDataSource) {
        if (sInstance == null)
            sInstance = new TracksRepository(tracksRemoteDataSource, tracksLocalDataSource);
        return sInstance;
    }

    public static void destroyInstance() { sInstance = null; }
}
