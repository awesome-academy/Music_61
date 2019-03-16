package com.sun.music61.data.adapter;

import com.sun.music61.R;
import com.sun.music61.data.model.Track;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class CustomSliderAdapter extends SliderAdapter {

    /**
     * API SoundCloud return:
     * JPEG, PNG and GIF are accepted when uploading
     * and will be encoded to multiple JPEGs in these formats:
     *
     *      large: 100×100 (default)
     *      t500x500: 500×500
     */
    private static final String LARGE = "large";
    private static final String T500 = "t500x500";

    private List<Track> mBanners;

    public CustomSliderAdapter(List<Track> banners) {
        mBanners = banners;
    }

    @Override
    public int getItemCount() {
        return mBanners != null ? mBanners.size() : 0;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        if (mBanners.get(position).getArtworkUrl() != null)
            imageSlideViewHolder.bindImageSlide(mBanners.get(position).getArtworkUrl().replace(LARGE, T500));
        else
            imageSlideViewHolder.bindImageSlide(R.drawable.ic_filter_hdr_white_124);
    }
}
