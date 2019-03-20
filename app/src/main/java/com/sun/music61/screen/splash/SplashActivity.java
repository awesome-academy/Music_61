package com.sun.music61.screen.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.sun.music61.R;
import com.sun.music61.screen.home.HomeActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    private static final int TIME_SLEEP = 500;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        autoSwitchHomeActivity();
    }

    private void autoSwitchHomeActivity() {
        new Thread(() -> {
            try {
                Thread.sleep(TIME_SLEEP);
            } catch (Exception ignored) {

            } finally {
                startActivity(HomeActivity.newInstance(getApplicationContext()));
                finish();
            }
        }).start();
    }
}
