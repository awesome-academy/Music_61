package com.sun.music61.util.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import com.sun.music61.R;
import com.sun.music61.data.model.Track;
import com.sun.music61.media.State;
import com.sun.music61.screen.MainActivity;
import com.sun.music61.screen.service.PlayTrackService;
import com.sun.music61.util.CommonUtils;
import com.sun.music61.util.helpers.ImageFactory;
import com.sun.music61.util.listener.FetchImageCallBack;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;
import static android.support.v4.media.app.NotificationCompat.MediaStyle;

public class MusicNotificationHelper extends NotificationHelper {

    private static final String TAG = MusicNotificationHelper.class.getName();
    protected static final int REQUEST_CODE = 10;
    protected static final int LARGE_ICON_SIZE = 100;
    private static final int ACTION_PREV = 0;
    private static final int ACTION_PLAY = 1;
    private static final int ACTION_NEXT = 2;
    private static final int ACTION_FAVORITE = 3;

    private NotificationCompat.Builder mBuilder;

    public MusicNotificationHelper(PlayTrackService service) {
        super(service);
    }

    public void createBuilder() {
        mBuilder = new NotificationCompat.Builder(mService, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pendingIntentOpenPlayActivity())
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(new MediaStyle()
                        .setShowActionsInCompactView(ACTION_PREV, ACTION_PLAY, ACTION_NEXT))
                .addAction(
                        R.drawable.ic_skip_previous_24dp,
                        mService.getString(R.string.btn_prev),
                        pendingIntentOpenPlayActivity()
                )
                .addAction(
                        R.drawable.ic_pause_24dp,
                        mService.getString(R.string.btn_pause),
                        pendingIntentOpenPlayActivity()
                )
                .addAction(
                        R.drawable.ic_skip_next_24dp,
                        mService.getString(R.string.btn_skip),
                        pendingIntentOpenPlayActivity()
                )
                .addAction(
                        R.drawable.ic_favorite_24dp_scaled,
                        mService.getString(R.string.fav_add),
                        pendingIntentOpenPlayActivity()
                )
                .setShowWhen(false)
                .setVisibility(VISIBILITY_PUBLIC);
    }

    private PendingIntent pendingIntentOpenPlayActivity() {
        Intent resultIntent = MainActivity.newInstance(mService);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mService);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        return stackBuilder.getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void updateTrack(Track track) {
        mBuilder.setContentTitle(track.getTitle())
                .setContentText(track.getUser().getUsername())
                .setLargeIcon(BitmapFactory.decodeResource(mService.getResources(), R.mipmap.ic_launcher));
        if (mService.getState() == State.PLAY)
            mService.startForeground(NOTIFICATION_ID, mBuilder.build());
        loadImageSong(track);
    }

    private void loadImageSong(Track track) {
        ImageFactory.Builder()
                .url(track.getArtworkUrl())
                .type(CommonUtils.T300)
                .width(LARGE_ICON_SIZE).height(LARGE_ICON_SIZE)
                .onListener(new FetchImageCallBack() {
                    @Override
                    public void onCompleted(Bitmap bitmap) {
                        mBuilder.setLargeIcon(bitmap);
                        MusicNotificationHelper.this.notify(NOTIFICATION_ID, mBuilder.build());
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.e(TAG, "onError: ", ex);
                    }
                })
                .build();
    }
}
