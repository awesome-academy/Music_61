package com.sun.music61.data.source.remote;

import android.util.Log;

import com.sun.music61.BuildConfig;
import com.sun.music61.data.model.Track;
import com.sun.music61.data.source.TracksDataSource;
import com.sun.music61.util.CommonUtils;
import com.sun.music61.util.helpers.ConnectAPIHelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.sun.music61.util.StatusCodeUtils.OK;

public class TracksRemoteDataSource implements TracksDataSource {

    private static final String TAG = TracksRemoteDataSource.class.getName();

    private static final String TAG_SONG= "songs";
    private static final int LIMIT_DEFAULT = 5;
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
    public List<Track> getBanners() {
        List<Track> banners = new ArrayList<>();
        HashMap<String, String> response;
        ConnectAPIHelpers connect = new ConnectAPIHelpers(
                CommonUtils.URL_SERVER + CommonUtils.APIReference.TRACKS + "?"
                        + CommonUtils.KeyParams.CLIENT_ID + BuildConfig.API_KEY + "&"
                        + CommonUtils.KeyParams.TAGS + TAG_SONG + "&"
                        + CommonUtils.KeyParams.LIMIT + LIMIT_DEFAULT + "&"
                        + CommonUtils.KeyParams.OFFSET + OFF_SET_NUMBER_DEFAULT
        );
        connect.execute();
        try {
            response = connect.get();
            return parseBanners(response);
        } catch (InterruptedException e) {
            Log.e(TAG, "getBanners: " + e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, "getBanners: " + e.getMessage());
        }
        return banners;
    }

    private List<Track> parseBanners(HashMap<String, String> response) {
        Log.e(TAG, "parseBanners: " + response);
        ArrayList<Track> banners = new ArrayList<>();
        int statusCode = Integer.parseInt(
                Objects.requireNonNull(response.get(ConnectAPIHelpers.Constants.STATUS_CODE))
        );
        switch (statusCode) {
            case OK:
                try {
                    String data = response.get(ConnectAPIHelpers.Constants.DATA);
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Track track = new Track(obj);
                        banners.add(track);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "parseBanners: " + e.getMessage());
                }
                break;
            default:
                Log.e(TAG, "parseBanners: " + statusCode);
                break;
        }
        return banners;
    }
}
