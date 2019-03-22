package com.sun.music61.screen.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun.music61.R;
import com.sun.music61.data.model.Track;
import com.sun.music61.screen.home.adapter.TrackAdapter;
import com.sun.music61.screen.home.contract.AllSongsContract;
import com.sun.music61.screen.home.presenter.AllSongPresenter;
import com.sun.music61.util.RepositoryInstance;
import com.sun.music61.util.listener.ItemRecyclerOnClickListener;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.sun.music61.util.CommonUtils.Genres;

public class AllSongsFragment extends Fragment implements AllSongsContract.View,
        ItemRecyclerOnClickListener {

    private static final String OFFSET_DEFAULT = "0";

    private AllSongsContract.Presenter mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerMusics;
    private TrackAdapter mMusicAdapter;
    private RecyclerView mRecyclerAudios;
    private TrackAdapter mAudioAdapter;

    public static AllSongsFragment newInstance() {
        return new AllSongsFragment();
    }

    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_songs_fragment, container, false);
        initView(rootView);
        mPresenter = new AllSongPresenter(
                RepositoryInstance.getInstanceTrackRepository(Objects.requireNonNull(getContext())),
                this);
        onListenerEvent();
        return rootView;
    }

    private void initView(View rootView) {
        mRecyclerMusics = rootView.findViewById(R.id.recyclerMusic);
        mRecyclerAudios = rootView.findViewById(R.id.recyclerAudio);
        mMusicAdapter = new TrackAdapter(R.layout.item_track_square);
        mAudioAdapter = new TrackAdapter(R.layout.item_track_square);
        mMusicAdapter.setOnItemClickListener(this);
        mAudioAdapter.setOnItemClickListener(this);
        mRecyclerMusics.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerAudios.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerMusics.setAdapter(mMusicAdapter);
        mRecyclerAudios.setAdapter(mAudioAdapter);
        mRefreshLayout = rootView.findViewById(R.id.swipeLayout);
        mRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
    }

    private void onListenerEvent() {
        mRefreshLayout.post(this::loadData);
        mRefreshLayout.setOnRefreshListener(() -> {
            loadData();
            mRefreshLayout.setRefreshing(false);
        });
    }

    private void loadData() {
        mPresenter.loadAllTracks(Genres.ALL_MUSIC, OFFSET_DEFAULT);
        mPresenter.loadAllTracks(Genres.ALL_AUDIO, OFFSET_DEFAULT);
    }

    @Override
    public void setPresenter(AllSongsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onGetTracksSuccess(String genres, List<Track> tracks) {
        switch (genres) {
            case Genres.ALL_MUSIC:
                mRecyclerMusics.setVisibility(View.VISIBLE);
                mMusicAdapter.updateData(tracks);
                break;
            case Genres.ALL_AUDIO:
                mRecyclerAudios.setVisibility(View.VISIBLE);
                mAudioAdapter.updateData(tracks);
                break;
        }
    }

    @Override
    public void onDataTracksNotAvailable(String genres) {
        switch (genres) {
            case Genres.ALL_MUSIC:
                mRecyclerMusics.setVisibility(View.GONE);
                break;
            case Genres.ALL_AUDIO:
                mRecyclerAudios.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showErrors(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerItemClick(Object object, int position) {
    }
}
