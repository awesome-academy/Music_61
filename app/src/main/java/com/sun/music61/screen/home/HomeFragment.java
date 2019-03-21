package com.sun.music61.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.music61.R;
import com.sun.music61.screen.home.adapter.HomePagerAdapter;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
