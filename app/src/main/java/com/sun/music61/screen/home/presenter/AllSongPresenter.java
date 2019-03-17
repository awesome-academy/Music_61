package com.sun.music61.screen.home.presenter;

import android.support.annotation.NonNull;
import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.RepositoryCallBack;
import com.sun.music61.data.source.repository.TracksRepository;
import com.sun.music61.screen.home.contract.AllSongsContract;
import com.sun.music61.util.CommonUtils;
import java.util.ArrayList;
import java.util.List;

public class AllSongPresenter implements AllSongsContract.Presenter {

    @NonNull
    private final TracksRepository mRepository;
    @NonNull
    private final AllSongsContract.View mView;

    public AllSongPresenter(@NonNull TracksRepository repository,
            @NonNull AllSongsContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadAllTracks(String genres, String offset) {
        mRepository.getTracksByGenres(genres, offset,
                new RepositoryCallBack() {
                    @Override
                    public <T> void onSuccess(List<T> objects) {
                        if (!objects.isEmpty()) {
                            mView.onGetTracksSuccess(genres, (ArrayList<Track>) objects);
                        } else {
                            mView.onDataTracksNotAvailable(genres);
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        mView.showErrors(throwable.getMessage());
                    }
                });
    }
}
