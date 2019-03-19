package com.sun.music61.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sun.music61.data.source.local.TracksLocalDataSource;
import com.sun.music61.data.source.remote.TracksRemoteDataSource;
import com.sun.music61.data.source.repository.TracksRepository;

public class RepositoryInstance {

    public static TracksRepository getInstanceTrackRepository(@NonNull Context context) {
        return TracksRepository.getInstance(
                TracksRemoteDataSource.getInstance(),
                TracksLocalDataSource.getInstance(context)
        );
    }
}