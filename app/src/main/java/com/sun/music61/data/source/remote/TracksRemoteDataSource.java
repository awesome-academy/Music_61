package com.sun.music61.data.source.remote;

import android.util.Log;
import com.sun.music61.data.model.Response;
import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.RepositoryCallBack;
import com.sun.music61.data.source.TracksDataSource;
import com.sun.music61.util.CommonUtils;
import com.sun.music61.util.helpers.APIFactory;
import com.sun.music61.util.listener.APICallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sun.music61.util.StatusCodeUtils.OK;

public class TracksRemoteDataSource implements TracksDataSource.RemoteDataSource {

    private static final String TAG = TracksRemoteDataSource.class.getName();
    private static final String TAG_SONG = "songs";
    private static final String ORDER = "created_at";
    private static final int LIMIT_BANNER = 5;
    private static final int LIMIT_DEFAULT = 10;
    private static final int OFF_SET_NUMBER_DEFAULT = 1;

    private static TracksRemoteDataSource sInstance;

    public static TracksRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TracksRemoteDataSource();
        }
        return sInstance;
    }

    private TracksRemoteDataSource() {
    }

    @Override
    public void getBanners(RepositoryCallBack callback) {
        APIFactory.Builder()
                .baseUrl(CommonUtils.API_TRACKS
                        + CommonUtils.KeyParams.TAGS + TAG_SONG + "&"
                        + CommonUtils.KeyParams.LIMIT + LIMIT_BANNER + "&"
                        + CommonUtils.KeyParams.OFFSET + OFF_SET_NUMBER_DEFAULT)
                .method(APIFactory.Method.GET)
                .enqueue(new APICallback() {
                    @Override
                    public void onResponse(Response response) {
                        callback.onSuccess(parseTracks(response));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailed(throwable);
                    }
                })
                .build();
    }

    @Override
    public void getTracksByGenres(String genres, String offset, RepositoryCallBack callback) {
        APIFactory.Builder()
                .baseUrl(CommonUtils.API_TRACKS
                        + CommonUtils.KeyParams.GENRES + genres + "&"
                        + CommonUtils.KeyParams.ORDER + ORDER + "&"
                        + CommonUtils.KeyParams.LIMIT + LIMIT_DEFAULT + "&"
                        + CommonUtils.KeyParams.OFFSET + offset)
                .method(APIFactory.Method.GET)
                .enqueue(new APICallback() {
                    @Override
                    public void onResponse(Response response) {
                        callback.onSuccess(parseTracks(response));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailed(throwable);
                    }
                })
                .build();
    }

    private List<Track> parseTracks(Response response) {
        ArrayList<Track> tracks = new ArrayList<>();
        switch (response.getStatusCode()) {
            case OK:
                try {
                    StringBuffer data = response.getResult();
                    JSONArray array = new JSONArray(data.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Track track = new Track(obj);
                        tracks.add(track);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "parseTracks: " + e.getMessage());
                }
                break;
            default:
                Log.e(TAG, "parseTracks: " + response.getStatusCode());
                break;
        }
        return tracks;
    }
}
