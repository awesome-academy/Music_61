package com.sun.music61.screen.home;

import com.sun.music61.BasePresenter;
import com.sun.music61.BaseView;
import com.sun.music61.data.model.Track;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface HomeContract {

    interface HomeActivityView extends BaseView<Presenter> {
        void showBanners(List<Track> banners);
        void showNoBanners();
    }

    interface Presenter extends BasePresenter {
        void loadBanners();
    }
}
