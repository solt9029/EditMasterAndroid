package com.solt9029.editmasterandroid.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.solt9029.editmasterandroid.constant.NumberConstants;
import com.solt9029.editmasterandroid.constant.PercentageConstants;
import com.solt9029.editmasterandroid.constant.PositionConstants;

public final class CalcUtil {
    public static float convertPx2Dp(int px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.density;
    }

    public static float convertDp2Px(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * metrics.density;
    }

    public static float calcSecondsPerNote(float bpm) {
        float barPerMinute = bpm / NumberConstants.BEAT;
        float barPerSecond = barPerMinute / 60;
        float notesPerSecond = barPerSecond * NumberConstants.NOTES_PER_BAR;
        return 1f / notesPerSecond;
    }

    public static NoteIndexRange<Double> calcNoteIndexRangeInSecondRange(double secondRange, float currentTime, float bpm, float offset) {
        double first = Math.ceil((currentTime - secondRange - offset) / calcSecondsPerNote(bpm));
        double last = Math.floor((currentTime + secondRange - offset) / calcSecondsPerNote(bpm));
        return new NoteIndexRange<>(first, last);
    }

    public static float calcFirstNoteX(float currentTime, float bpm, float offset, float speed) {
        float spaceWidth = speed * PercentageConstants.Player.SPEED_TO_SPACE_WIDTH;
        return PositionConstants.Player.Judge.X + ((offset - currentTime) / calcSecondsPerNote(bpm)) * spaceWidth;
    }
}
