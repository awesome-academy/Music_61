package com.sun.music61.util;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.sun.music61.util.helpers.ImageFactory;
import com.sun.music61.util.listener.FetchImageCallBack;

public class CommonUtils {

    public static final String URL_SERVER = "http://api.soundcloud.com";

    /**
     * API SoundCloud return:
     * JPEG, PNG and GIF are  will be encoded to multiple JPEGs in these formats:
     * large: 100×100 (default)
     * t300x300: 300×300
     * t500x500: 500×500
     */
    public static final String LARGE = "large";
    public static final String T500 = "t500x500";
    public static final String T300 = "t300x300";

    public interface APIReference {
        String TRACKS = "/tracks";
    }

    public interface KeyParams {
        String CLIENT_ID = "client_id=";
        String TAGS = "tags=";
        String LIMIT = "limit=";
        String OFFSET = "offset=";
    }

    public interface Genres {
        String ALL_MUSIC = "all-music";
        String ALL_AUDIO = "all-audio";
        String ALTERNATIVE_ROCK = "alternativerock";
        String AMBIENT = "ambient";
        String CLASSICAL = "classical";
        String COUNTRY = "country";
    }

    public interface TitleFragment {
        String ALL = "All";
        String ALTERNATIVE_ROCK = "Rock";
        String AMBIENT = "Ambient";
        String CLASSICAL = "Classical";
        String COUNTRY = "Country";
    }

    public static void loadImageFromUrl(ImageView image, String url, String type) {
        ImageFactory.Builder()
                .url(url)
                .type(type)
                .width(image.getMeasuredWidth())
                .height(image.getMeasuredHeight())
                .onListener(new FetchImageCallBack() {
                    @Override
                    public void onCompleted(Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                })
                .build();
    }
}
