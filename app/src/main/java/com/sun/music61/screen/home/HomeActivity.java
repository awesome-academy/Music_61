package com.sun.music61.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.sun.music61.R;
import com.sun.music61.screen.home.adapter.CustomSliderAdapter;
import com.sun.music61.screen.home.adapter.HomePagerAdapter;
import com.sun.music61.data.model.Track;
import com.sun.music61.screen.home.contract.HomeContract;
import com.sun.music61.screen.home.presenter.HomePresenter;
import com.sun.music61.util.RepositoryInstance;
import com.sun.music61.util.helpers.ImageLoadingServiceHelpers;
import java.util.List;
import ss.com.bannerslider.Slider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private HomeContract.Presenter mPresenter;
    private Slider mSlider;

    public static Intent newInstance(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Notes : add this code before setContentView
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder().setDefaultFontPath("fonts/arkhip.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        setContentView(R.layout.home_activity);
        initView();
        mPresenter = new HomePresenter(
                RepositoryInstance.getInstanceTrackRepository(getApplicationContext()), this);
        mPresenter.loadBanners();
        onListenerEvent();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_home);
        toolbar.setSubtitle(R.string.sub_title_home);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        mSlider = findViewById(R.id.slider);
        Slider.init(new ImageLoadingServiceHelpers());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void onListenerEvent() {
        mSlider.setOnSlideClickListener(position -> {
            // code late
        });
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onGetBannersSuccess(List<Track> banners) {
        mSlider.setVisibility(View.VISIBLE);
        mSlider.setAdapter(new CustomSliderAdapter(banners));
    }

    @Override
    public void onDataBannersNotAvailable() {
        mSlider.setVisibility(View.GONE);
    }

    @Override
    public void showErrors(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                // Code late
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
