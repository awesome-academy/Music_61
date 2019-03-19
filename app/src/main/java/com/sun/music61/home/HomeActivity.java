package com.sun.music61.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sun.music61.R;
import com.sun.music61.data.adapter.CustomSliderAdapter;
import com.sun.music61.data.model.Track;
import com.sun.music61.util.RepositoryInstance;
import com.sun.music61.util.helpers.ImageLoadingServiceHelpers;

import java.util.List;

import ss.com.bannerslider.Slider;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeActivity extends AppCompatActivity implements HomeContract.HomeActivityView {

    private static final String TAG = HomeActivity.class.getName();

    private HomeContract.Presenter mPresenter;
    private Slider mSlider;

    public static Intent newInstance(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        // Declare presenter
        mPresenter = new HomePresenter(RepositoryInstance.getInstanceTrackRepository(
                getApplicationContext()),
                this
        );
        initView();
        mPresenter.loadBanners();
        handleEvent();
    }

    private void initView() {
        mSlider = findViewById(R.id.slider);
        Slider.init(new ImageLoadingServiceHelpers());
    }

    private void handleEvent() {
        mSlider.setOnSlideClickListener(position -> {
            // code late
        });
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showBanners(List<Track> banners) {
        mSlider.setVisibility(View.VISIBLE);
        mSlider.setAdapter(new CustomSliderAdapter(banners));
    }

    @Override
    public void showNoBanners() {
        mSlider.setVisibility(View.GONE);
    }

    @Override
    public void showErrors(String message) {
        Log.e(TAG, "showErrors: " + message);
    }
}
