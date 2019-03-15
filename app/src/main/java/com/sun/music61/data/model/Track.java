package com.sun.music61.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Immutable model class for a Track.
 */
public final class Track implements Parcelable {

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @NonNull
    private String mId;

    @NonNull
    private String mDuration;

    @NonNull
    private String mGenre;

    @Nullable
    private String mTitle;

    @Nullable
    private String mDescription;

    @Nullable
    private String mOriginalFormat;

    @NonNull
    private long mOriginalContentSize;

    @Nullable
    private String mVideoURL;

    @NonNull
    private User mUser;

    @Nullable
    private String mPermalinkUrl;

    @Nullable
    private String mArtworkUrl;

    @Nullable
    private String mStreamUrl;

    @Nullable
    private String mDownloadUrl;

    @Nullable
    private int mPlaybackCount;

    @Nullable
    private int mDownloadCount;

    @Nullable
    private int mFavoringCount;

    @Nullable
    private int mCommentCount;

    @Nullable
    private boolean mDownloadAble;

    @Nullable
    private String mWaveFormUrl;

    @Nullable
    private String mAttachmentsUri;

    interface JSONKey {
        String ID = "id";
        String DURATION = "duration";
        String GENRE = "genre";
        String TITLE = "title";
        String DESCRIPTION = "description";
        String ORIGINAL_FORMAT = "original_format";
        String ORIGINAL_CONTENT_SIZE = "original_content_size";
        String VIDEO_URL = "video_url";
        String USER = "user";
        String PERMALINK_URL = "permalink_url";
        String ART_WORK_URL = "artwork_url";
        String STREAM_URL = "stream_url";
        String DOWNLOAD_URL = "download_url";
        String PLAYBACK_COUNT = "playback_count";
        String DOWNLOAD_COUNT = "download_count";
        String FAVORING_COUNT = "favoritings_count";
        String COMMENT_COUNT = "comment_count";
        String DOWNLOAD_ABLE = "downloadable";
        String WAVE_FROM_URL = "waveform_url";
        String ATTACHMENTS_URI = "attachments_uri";
    }

    private Track(Parcel in) {
        mId = Objects.requireNonNull(in.readString());
        mDuration = Objects.requireNonNull(in.readString());
        mGenre = Objects.requireNonNull(in.readString());
        mTitle = in.readString();
        mDescription = in.readString();
        mOriginalFormat = in.readString();
        mOriginalContentSize = in.readLong();
        mVideoURL = in.readString();
        mPermalinkUrl = in.readString();
        mArtworkUrl = in.readString();
        mStreamUrl = in.readString();
        mDownloadUrl = in.readString();
        mPlaybackCount = in.readInt();
        mDownloadCount = in.readInt();
        mFavoringCount = in.readInt();
        mCommentCount = in.readInt();
        mDownloadAble = in.readByte() != 0;
        mWaveFormUrl = in.readString();
        mAttachmentsUri = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mDuration);
        parcel.writeString(mGenre);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mOriginalFormat);
        parcel.writeLong(mOriginalContentSize);
        parcel.writeString(mVideoURL);
        parcel.writeString(mPermalinkUrl);
        parcel.writeString(mArtworkUrl);
        parcel.writeString(mStreamUrl);
        parcel.writeString(mDownloadUrl);
        parcel.writeInt(mPlaybackCount);
        parcel.writeInt(mDownloadCount);
        parcel.writeInt(mFavoringCount);
        parcel.writeInt(mCommentCount);
        parcel.writeByte((byte) (mDownloadAble ? 1 : 0));
        parcel.writeString(mWaveFormUrl);
        parcel.writeString(mAttachmentsUri);
    }

    public Track(JSONObject obj) throws JSONException {
        mId = obj.getString(JSONKey.ID);
        mDuration = obj.getString(JSONKey.DURATION);
        mGenre = obj.getString(JSONKey.GENRE);
        mTitle = obj.getString(JSONKey.TITLE);
        mDescription = obj.getString(JSONKey.DESCRIPTION);
        mOriginalFormat = obj.getString(JSONKey.ORIGINAL_FORMAT);
        mOriginalContentSize = obj.getLong(JSONKey.ORIGINAL_CONTENT_SIZE);
        mVideoURL = obj.getString(JSONKey.VIDEO_URL);
        mUser = new User(obj.getJSONObject(JSONKey.USER));
        mPermalinkUrl = obj.getString(JSONKey.PERMALINK_URL);
        mArtworkUrl = obj.getString(JSONKey.ART_WORK_URL);
        mStreamUrl = obj.getString(JSONKey.STREAM_URL);
        mDownloadUrl = obj.getString(JSONKey.DOWNLOAD_URL);
        mPlaybackCount = obj.getInt(JSONKey.PLAYBACK_COUNT);
        mDownloadCount = obj.getInt(JSONKey.DOWNLOAD_COUNT);
        mFavoringCount = obj.getInt(JSONKey.FAVORING_COUNT);
        mCommentCount = obj.getInt(JSONKey.COMMENT_COUNT);
        mDownloadAble = obj.getBoolean(JSONKey.DOWNLOAD_ABLE);
        mWaveFormUrl = obj.getString(JSONKey.WAVE_FROM_URL);
        mAttachmentsUri = obj.getString(JSONKey.ATTACHMENTS_URI);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    @NonNull
    public String getGenre() {
        return mGenre;
    }

    public void setGenre(@NonNull String genre) {
        this.mGenre = genre;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String title) {
        this.mTitle = title;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@Nullable String description) {
        this.mDescription = description;
    }

    @Nullable
    public String getOriginalFormat() {
        return mOriginalFormat;
    }

    public void setOriginalFormat(@Nullable String originalFormat) {
        this.mOriginalFormat = originalFormat;
    }

    public long getOriginalContentSize() {
        return mOriginalContentSize;
    }

    public void setOriginalContentSize(long originalContentSize) {
        this.mOriginalContentSize = originalContentSize;
    }

    @Nullable
    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(@Nullable String videoURL) {
        this.mVideoURL = videoURL;
    }

    @NonNull
    public User getUser() {
        return mUser;
    }

    public void setUser(@NonNull User user) {
        this.mUser = user;
    }

    @Nullable
    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setPermalinkUrl(@Nullable String permalinkUrl) {
        this.mPermalinkUrl = permalinkUrl;
    }

    @Nullable
    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(@Nullable String artworkUrl) {
        this.mArtworkUrl = artworkUrl;
    }

    @Nullable
    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setStreamUrl(@Nullable String streamUrl) {
        this.mStreamUrl = streamUrl;
    }

    @Nullable
    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(@Nullable String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        this.mPlaybackCount = playbackCount;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.mDownloadCount = downloadCount;
    }

    public int getFavoritingCount() {
        return mFavoringCount;
    }

    public void setFavoritingCount(int favoritingCount) {
        this.mFavoringCount = favoritingCount;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        this.mCommentCount = commentCount;
    }

    public boolean ismDownloadAble() {
        return mDownloadAble;
    }

    public void setDownloadAble(boolean downloadAble) {
        this.mDownloadAble = downloadAble;
    }

    @Nullable
    public String getWaveFormUrl() {
        return mWaveFormUrl;
    }

    public void setWaveFormUrl(@Nullable String waveFormUrl) {
        this.mWaveFormUrl = waveFormUrl;
    }

    public String getAttachmentsUri() {
        return mAttachmentsUri;
    }

    public void setAttachmentsUri(String attachmentsUri) {
        this.mAttachmentsUri = attachmentsUri;
    }
}
