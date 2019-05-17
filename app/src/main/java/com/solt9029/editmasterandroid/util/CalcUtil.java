package com.solt9029.editmasterandroid.util;

import android.content.Context;
import android.util.DisplayMetrics;
import androidx.annotation.Nullable;
import com.solt9029.editmasterandroid.constants.NumberConstants;
import com.solt9029.editmasterandroid.constants.PercentageConstants;
import com.solt9029.editmasterandroid.constants.PositionConstants;
import com.solt9029.editmasterandroid.constants.SizeConstants;

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

    public static IndexRange calcNoteIndexRangeInSecondRange(double secondRange, float currentTime, float bpm,
                                                             float offset) {
        int first = (int) Math.ceil((currentTime - secondRange - offset) / calcSecondsPerNote(bpm));
        int last = (int) Math.floor((currentTime + secondRange - offset) / calcSecondsPerNote(bpm));
        return new IndexRange(first, last);
    }

    public static float calcFirstNoteX(float currentTime, float bpm, float offset, float speed) {
        float spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH;
        return PositionConstants.PLAYER_JUDGE_X + ((offset - currentTime) / calcSecondsPerNote(bpm)) * spaceWidth;
    }

    public static IndexRange calcNoteIndexRangeInEditor(int notesSize, int translateYPx, int heightPx,
                                                        Context context) {
        final int firstBarIndex = (int) Math
                .floor((double) convertPx2Dp(translateYPx, context) / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT);
        final int lastBarIndex = (int) Math.ceil((double) convertPx2Dp(translateYPx + heightPx, context) /
                SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT);

        final int firstNoteIndex = firstBarIndex * NumberConstants.NOTES_PER_BAR;
        int lastNoteIndex = lastBarIndex * NumberConstants.NOTES_PER_BAR - 1;
        if (lastNoteIndex >= notesSize) {
            lastNoteIndex = notesSize - 1;
        }

        return new IndexRange(firstNoteIndex, lastNoteIndex);
    }

    public static IndexRange calcBarIndexRangeInEditor(int barNum, int translateYPx, int heightPx,
                                                       Context context) {
        final int first = (int) Math
                .floor((double) convertPx2Dp(translateYPx, context) / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT);
        int last = (int) Math.ceil((double) convertPx2Dp(translateYPx + heightPx, context) /
                SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT);
        if (last >= barNum) {
            last = barNum - 1;
        }
        return new IndexRange(first, last);
    }

    @Nullable
    public static IndexRange calcNoteIndexRangeInPlayer(int notesSize, float speed, int widthPx, int firstNoteX) {
        final float spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH;
        int first = (int) Math.floor(-firstNoteX / spaceWidth) - 3;
        final int noteNum = (int) Math.ceil((widthPx - 1) / spaceWidth);
        int last = first + noteNum + 6;

        if (first < 0) {
            first = 0;
        }
        if (first >= notesSize) {
            return null;
        }

        if (last < 0) {
            return null;
        }
        if (last >= notesSize) {
            last = notesSize - 1;
        }

        return new IndexRange(first, last);
    }

    public static float calcEditorHeightPx(int notesSize, Context context) {
        float heightDp = (float) notesSize / NumberConstants.NOTES_PER_BAR * SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT;
        return convertDp2Px(heightDp, context);
    }

    public static float calcBarWidth(int widthPx, Context context) {
        return convertPx2Dp(widthPx, context) - PositionConstants.EDITOR_BAR_X * 2;
    }

    public static float calcBarWidthPx(int widthPx, Context context) {
        return convertDp2Px(calcBarWidth(widthPx, context), context);
    }

    public static int calcBarNum(int notesSize) {
        return (int) Math.ceil((double) notesSize / NumberConstants.NOTES_PER_BAR);
    }
}
