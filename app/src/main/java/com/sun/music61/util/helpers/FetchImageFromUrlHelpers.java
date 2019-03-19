package com.sun.music61.util.helpers;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchImageFromUrlHelpers extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = FetchImageFromUrlHelpers.class.getName();

    @SuppressLint("StaticFieldLeak")
    private ImageView mImage;

    public FetchImageFromUrlHelpers(ImageView image) {
        mImage = image;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new URL(urlDisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mImage.setImageBitmap(result);
    }
}
