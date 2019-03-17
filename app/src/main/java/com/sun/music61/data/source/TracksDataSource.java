package com.sun.music61.data.source;

public interface TracksDataSource {

    interface RemoteDataSource {
        void getBanners(RepositoryCallBack callback);
        void getTracksByGenres(String genres, String offset, RepositoryCallBack callback);
    }

    interface LocalDataSource {
    }
}
