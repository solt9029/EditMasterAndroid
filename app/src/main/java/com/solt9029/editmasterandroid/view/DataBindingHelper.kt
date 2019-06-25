package com.solt9029.editmasterandroid.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.view.customview.EditorBarsNotesView
import com.solt9029.editmasterandroid.view.customview.EditorCurrentTimeMarkView
import com.solt9029.editmasterandroid.view.customview.PlayerNotesView
import com.solt9029.editmasterandroid.view.customview.ScrollContainerView
import timber.log.Timber

object DataBindingHelper {
    @JvmStatic @BindingAdapter("youtube_image")
    fun setYoutubeImage(view: ImageView, videoId: String) {
        val url = "http://i.ytimg.com/vi/$videoId/mqdefault.jpg"
        Glide.with(view.context).load(url).into(view)
    }

    @JvmStatic @BindingAdapter("created_at")
    fun setCreatedAt(view: TextView, date: String) {
        view.text = "created at $date"
    }

    @JvmStatic @BindingAdapter("translate_y_px")
    fun setTranslateYPx(view: EditorBarsNotesView, translateYPx: Int) {
        view.setTranslateYPx(translateYPx)
    }

    @JvmStatic @BindingAdapter("notes")
    fun setNotes(view: EditorBarsNotesView, notes: List<Int>) {
        view.setNotes(notes)
    }

    @JvmStatic @BindingAdapter("bpm")
    fun setBpm(view: EditorCurrentTimeMarkView, bpm: String) {
        view.setBpm(bpm.toFloat())
    }

    @JvmStatic @BindingAdapter("offset")
    fun setOffset(view: EditorCurrentTimeMarkView, offset: String) {
        view.setOffset(offset.toFloat())
    }

    @JvmStatic @BindingAdapter("current_time")
    fun setCurrentTime(view: EditorCurrentTimeMarkView, currentTime: Float) {
        view.setCurrentTime(currentTime)
    }

    @JvmStatic @BindingAdapter("translate_y_px")
    fun setTranslateYPx(view: EditorCurrentTimeMarkView, translateYPx: Int) {
        view.setTranslateYPx(translateYPx)
    }

    @JvmStatic @BindingAdapter("notes", "context")
    fun setHeight(view: RelativeLayout, notes: List<Int>, context: Context) {
        val params = view.layoutParams
        params.height = CalcUtil.calcEditorHeightPx(notes.size, context).toInt()
        Timber.d("editorHeightPx: " + params.height)
        view.layoutParams = params
    }

    @JvmStatic @BindingAdapter("onScrollChange")
    fun setOnScrollChangeListener(view: ScrollContainerView,
                                  listener: ScrollContainerView.OnScrollChangeListener) {
        view.setOnScrollChangeListener(listener)
    }

    @JvmStatic @BindingAdapter("onTouch")
    fun setOnTouchListener(view: ScrollContainerView, listener: View.OnTouchListener) {
        view.setOnTouchListener(listener)
    }

    @JvmStatic @BindingAdapter("you_tube_player_listener")
    fun setYouTubePlayerListener(view: YouTubePlayerView, listener: AbstractYouTubePlayerListener) {
        view.addYouTubePlayerListener(listener)
    }

    @JvmStatic @BindingAdapter("onPositionChange")
    fun setOnPositionChangeListener(view: RadioRealButtonGroup,
                                    listener: RadioRealButtonGroup.OnPositionChangedListener) {
        view.setOnPositionChangedListener(listener)
    }

    @JvmStatic @BindingAdapter("current_time")
    fun setCurrentTime(view: PlayerNotesView, currentTime: Float) {
        view.setCurrentTime(currentTime)
    }

    @JvmStatic @BindingAdapter("bpm")
    fun setBpm(view: PlayerNotesView, bpm: String) {
        view.setBpm(bpm.toFloat())
    }

    @JvmStatic @BindingAdapter("offset")
    fun setOffset(view: PlayerNotesView, offset: String) {
        view.setOffset(offset.toFloat())
    }

    @JvmStatic @BindingAdapter("notes")
    fun setNotes(view: PlayerNotesView, notes: List<Int>) {
        view.setNotes(notes)
    }

    @JvmStatic @BindingAdapter("states")
    fun setStates(view: PlayerNotesView, states: List<Int>) {
        view.setStates(states)
    }

    @JvmStatic @BindingAdapter("speed")
    fun setSpeed(view: PlayerNotesView, speed: String) {
        view.setSpeed(speed.toFloat())
    }
}
