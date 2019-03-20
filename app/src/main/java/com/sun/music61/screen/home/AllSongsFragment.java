package com.sun.music61.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.music61.R;

public class AllSongsFragment extends Fragment {

    public static AllSongsFragment newInstance() {
        return new AllSongsFragment();
    }

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_songs_fragment, container, false);
    }
}
