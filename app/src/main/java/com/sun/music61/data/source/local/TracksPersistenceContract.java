package com.sun.music61.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tracks locally.
 */
public final class TracksPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private TracksPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TrackEntry implements BaseColumns {
        // Code late
    }
}
