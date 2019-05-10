package com.solt9029.editmasterandroid.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solt9029.editmasterandroid.view.customview.EditorBarsView;

import java.util.List;

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

    @BindingAdapter("translateY")
    public static void setTranslateY(EditorBarsView view, int translateY) {
        view.setTranslateY(translateY);
    }

    @BindingAdapter("notes")
    public static void setNotes(EditorBarsView view, List<Integer> notes) {
        view.setNotes(notes);
    }
}
