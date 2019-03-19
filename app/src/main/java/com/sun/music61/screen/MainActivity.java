package com.sun.music61.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.sun.music61.R;
import com.sun.music61.util.ActivityUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sun.music61.util.CommonUtils.Font;

public class MainActivity extends AppCompatActivity {

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static void replaceFragment(@NonNull AppCompatActivity activity, Fragment fragment) {
        ActivityUtils.replaceFragmentToActivity(activity.getSupportFragmentManager(), fragment,
                R.id.contentMain);
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
        // Default Fragment
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), MainFragment.newInstance(),
                R.id.contentMain);
    }
}
