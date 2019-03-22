package com.sun.music61.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.sun.music61.R;
import com.sun.music61.screen.home.HomeFragment;
import com.sun.music61.util.ActivityUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sun.music61.util.CommonUtils.Font;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMenuBottom;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
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
                new CalligraphyConfig.Builder().setDefaultFontPath(Font.ARKHIP)
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        setContentView(R.layout.main_activity);
        initView();
        // Default Fragment
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                HomeFragment.newInstance(), R.id.contentFrame);
        onEventListener();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle(R.string.sub_title_slogan);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        mMenuBottom = findViewById(R.id.menuBottom);
        mMenuBottom.setSelectedItemId(R.id.actionHome);
    }

    private void onEventListener() {
        mMenuBottom.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.actionHome:
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        HomeFragment.newInstance(), R.id.contentFrame);
                break;
            case R.id.actionMySong:
            case R.id.actionFavorite:
            case R.id.actionSetting:
                Toast.makeText(this, getString(R.string.text_coming_soon), Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return false;
    }
}
