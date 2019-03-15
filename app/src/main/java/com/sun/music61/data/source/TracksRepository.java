package com.sun.music61.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.sun.music61.data.model.Track;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tracks from the data sources into a cache.
 * </p>
 * For simplicity, this implements a dump synchronisation between locally persisted data
 * and data obtained from the server, by using the remote data source only if the local database
 * doesn't exists or is empty
 */
public class TracksRepository implements TracksDataSource {

    @Nullable
    private static TracksRepository INSTANCE = null;

    @NonNull
    private final TracksDataSource mTracksRemoteDataSource;

    @NonNull
    private final TracksDataSource mTracksLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests
     */
    @VisibleForTesting
    @Nullable
    public Map<String, Track> mCachedTracks;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    public boolean mCachedIsDirty = false;

    // Prevent direct instantiation
    private TracksRepository(@NonNull TracksDataSource tracksRemoteDataSource, @NonNull TracksDataSource tracksLocalDataSource) {
        mTracksRemoteDataSource = checkNotNull(tracksRemoteDataSource);
        mTracksLocalDataSource = checkNotNull(tracksLocalDataSource);
    }

    /**
     * Return the single instance of this class, creating it if necessary
     *
     * @param tracksRemoteDataSource  the backend data source
     * @param tracksLocalDataSource the device storage data source
     * @return the {@link TracksRepository} instance
     */
    public static TracksRepository getInstance(@NonNull TracksDataSource tracksRemoteDataSource, @NonNull TracksDataSource tracksLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new TracksRepository(tracksRemoteDataSource, tracksLocalDataSource);
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(TracksDataSource, TracksDataSource)}
     * to create a new instance next time it's called
     */
    public static void destroyInstance() { INSTANCE = null; }

    @Override
    public List<Track> getTracks() {
        // Respond immediately with cache if available and not dirty
        if (mCachedTracks != null && !mCachedIsDirty)
            return (List<Track>) mCachedTracks.values();
        else if (mCachedTracks == null) mCachedTracks = new LinkedHashMap<>();

        List<Track> remoteTracks = getAndSaveRemoteTracks();

        if (mCachedIsDirty) return remoteTracks;
        else {
            // Query the local storage if available. If not, query the network.
            return getAndCacheLocalTracks();
        }
    }

    private List<Track> getAndCacheLocalTracks() {
        return mTracksLocalDataSource.getTracks();
    }

    private List<Track> getAndSaveRemoteTracks() {
        return mTracksRemoteDataSource.getTracks();
    }

    @Override
    public Track getTrack(@NonNull String trackId) {

        final Track cachedTrack = getTrackWithId(trackId);

        // Respond immediately with cache if available
        if (cachedTrack != null) return cachedTrack;

        // Load from server/persisted if needed.

        // Do in memory cache update to keep the app UI up to date
        if (mCachedTracks == null) mCachedTracks = new LinkedHashMap<>();

        // Is the track in the local data source? If not, query the network.
        Track localTrack = getTrackWithFromLocalRepository(trackId);
        Track remoteTrack = mTracksRemoteDataSource
                .getTrack(trackId);

        return remoteTrack != null ? remoteTrack : localTrack;
    }

    @Nullable
    private Track getTrackWithId(String trackId) {
        checkNotNull(trackId);
        if (mCachedTracks == null || mCachedTracks.isEmpty()) return null;
        else return mCachedTracks.get(trackId);
    }

    @NonNull
    private Track getTrackWithFromLocalRepository(String trackId) {
        return mTracksLocalDataSource
                .getTrack(trackId);
    }

    @Override
    public void refreshTracks() {
        mCachedIsDirty = true;
    }
}
