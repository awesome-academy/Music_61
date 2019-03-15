package com.sun.music61.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @NonNull
    private String mId;

    @Nullable
    private String mPermalink;

    @NonNull
    private String mUsername;

    @Nullable
    private String mUri;

    @Nullable
    private String mPermalinkUrl;

    @Nullable
    private String mAvatarUrl;

    interface JSONKey {
        String ID = "id";
        String PERMALINK = "permalink";
        String USERNAME = "username";
        String URI = "uri";
        String PERMALINK_URL = "permalink_url";
        String AVATAR_URL = "avatar_url";
    }

    private User(Parcel in) {
        mId = Objects.requireNonNull(in.readString());
        mPermalink = in.readString();
        mUsername = Objects.requireNonNull(in.readString());
        mUri = in.readString();
        mPermalinkUrl = in.readString();
        mAvatarUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mPermalink);
        parcel.writeString(mUsername);
        parcel.writeString(mUri);
        parcel.writeString(mPermalinkUrl);
        parcel.writeString(mAvatarUrl);
    }

    public User(JSONObject obj) throws JSONException {
        mId = obj.getString(JSONKey.ID);
        mPermalink = obj.getString(JSONKey.PERMALINK);
        mUsername = obj.getString(JSONKey.USERNAME);
        mUri = obj.getString(JSONKey.URI);
        mPermalinkUrl = obj.getString(JSONKey.PERMALINK_URL);
        mAvatarUrl = obj.getString(JSONKey.AVATAR_URL);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    @Nullable
    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(@Nullable String permalink) {
        this.mPermalink = permalink;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(@NonNull String username) {
        this.mUsername = username;
    }

    @Nullable
    public String getUri() {
        return mUri;
    }

    public void setmUri(@Nullable String uri) {
        this.mUri = uri;
    }

    @Nullable
    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setmPermalinkUrl(@Nullable String permalinkUrl) {
        this.mPermalinkUrl = permalinkUrl;
    }

    @Nullable
    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(@Nullable String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }
}
