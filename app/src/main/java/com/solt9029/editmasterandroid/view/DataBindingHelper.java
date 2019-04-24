package com.solt9029.editmasterandroid.view;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solt9029.editmasterandroid.view.customview.BaseSurfaceView;

import androidx.databinding.BindingAdapter;

public class DataBindingHelper {
    @BindingAdapter("youtube_image")
    public static void setYoutubeImage(ImageView view, String videoId) {
        String url = "http://i.ytimg.com/vi/" + videoId + "/mqdefault.jpg";
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("created_at")
    public static void setCreatedAt(TextView view, String date) {
        view.setText("created at " + date);
    }

    @BindingAdapter("top_margin")
    public static void setTranslateY(BaseSurfaceView view, int topMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin = topMargin; // px
        view.setLayoutParams(params);
        view.draw(topMargin);
    }

    @BindingAdapter("height")
    public static void setHeight(BaseSurfaceView view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }
}
