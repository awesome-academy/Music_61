package com.sun.music61.screen.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sun.music61.R;
import java.util.Objects;

public class GenresFragment extends Fragment {

    private static final String ARGUMENT_GENRES = "ARGUMENT_GENRES";

    private String mGenres;

    public static GenresFragment newInstance(String genres) {
        GenresFragment fragment = new GenresFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_GENRES, genres);
        fragment.setArguments(args);
        return fragment;
    }

    public GenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mGenres = Objects.requireNonNull(getArguments()).getString(ARGUMENT_GENRES);
        View root = inflater.inflate(R.layout.genres_fragment, container, false);
        TextView textGenres = root.findViewById(R.id.textGenres);
        textGenres.setText(mGenres);
        return root;
    }
}
