package com.sun.music61.util;

public class CommonUtils {

    public static final String URL_SERVER = "http://api.soundcloud.com";

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
}
