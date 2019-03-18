package com.sun.music61.data.source;

public interface TracksDataSource {

    interface RemoteDataSource {
        void getBanners(RepositoryCallBack callback);
        void getTracksByGenres(String genres, int offset, RepositoryCallBack callback);
    }

    interface LocalDataSource {
    }
}
