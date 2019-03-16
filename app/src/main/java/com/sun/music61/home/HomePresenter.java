package com.sun.music61.home;

import android.support.annotation.NonNull;

import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.repository.TracksRepository;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link HomeActivity}), retrieves the data and updates the
 * UI as required.
 */
public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final TracksRepository mTracksRepository;

    @NonNull
    private final HomeContract.HomeActivityView mHomeActivityView;

    public HomePresenter(@NonNull TracksRepository tracksRepository,
                         @NonNull HomeContract.HomeActivityView homeActivityView) {
        mTracksRepository = tracksRepository;
        mHomeActivityView = homeActivityView;

        mHomeActivityView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadBanners() {
        List<Track> banners = mTracksRepository.getBanners();
        if (!banners.isEmpty()) mHomeActivityView.showBanners(banners);
        else mHomeActivityView.showNoBanners();
    }
}
