package com.sun.music61.screen.home.presenter;

import android.support.annotation.NonNull;
import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.RepositoryCallBack;
import com.sun.music61.data.source.TracksDataSource;
import com.sun.music61.data.source.repository.TracksRepository;
import com.sun.music61.screen.home.contract.HomeContract;
import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final TracksRepository mRepository;
    @NonNull
    private HomeContract.View mView;

    public HomePresenter(@NonNull TracksRepository repository,
            @NonNull HomeContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadBanners() {
        mRepository.getBanners(new RepositoryCallBack() {
            @Override
            public <T> void onSuccess(List<T> objects) {
                if (!objects.isEmpty()) mView.onGetBannersSuccess((ArrayList<Track>) objects);
                else mView.onDataBannersNotAvailable();
            }

            @Override
            public void onFailed(Throwable throwable) {
                mView.showErrors(throwable.getMessage());
            }
        });
    }
}
