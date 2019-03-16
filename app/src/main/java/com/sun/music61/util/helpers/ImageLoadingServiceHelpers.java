package com.sun.music61.util.helpers;

import android.widget.ImageView;

import ss.com.bannerslider.ImageLoadingService;

public class ImageLoadingServiceHelpers implements ImageLoadingService {

    @Override
    public void loadImage(String url, ImageView imageView) {
        new FetchImageFromUrlHelpers(imageView).execute(url);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        imageView.setImageResource(resource);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        new FetchImageFromUrlHelpers(imageView).execute(url);
    }
}
