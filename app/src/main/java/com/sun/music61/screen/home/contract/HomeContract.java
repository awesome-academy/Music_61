package com.sun.music61.screen.home.contract;

import com.sun.music61.BasePresenter;
import com.sun.music61.BaseView;
import com.sun.music61.data.model.Track;

import java.util.List;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void onGetBannersSuccess(List<Track> banners);
        void onDataBannersNotAvailable();
    }

    interface Presenter extends BasePresenter {
        void loadBanners();
    }
}
