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
}
