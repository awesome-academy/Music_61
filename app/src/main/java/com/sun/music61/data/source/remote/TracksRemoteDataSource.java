package com.sun.music61.data.source.remote;

import com.sun.music61.data.source.TracksDataSource;

public class TracksRemoteDataSource implements TracksDataSource {

    private static TracksRemoteDataSource sInstance;

    public static TracksRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TracksRemoteDataSource();
        }
        return sInstance;
    }

    private TracksRemoteDataSource() {
    }
}
