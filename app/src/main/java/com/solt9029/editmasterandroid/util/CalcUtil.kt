package com.solt9029.editmasterandroid.util

import android.content.Context
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.PercentageConstants
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.constants.SizeConstants

object CalcUtil {
    @JvmStatic fun convertPx2Dp(px: Int, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }

    @JvmStatic fun convertPx2Dp(px: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }

    @JvmStatic fun convertDp2Px(dp: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return dp * metrics.density
    }

    @JvmStatic fun convertDp2Px(dp: Int, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return dp * metrics.density
    }

    @JvmStatic fun calcSecondsPerNote(bpm: Float): Float {
        val barPerMinute = bpm / NumberConstants.BEAT
        val barPerSecond = barPerMinute / 60
        val notesPerSecond = barPerSecond * NumberConstants.NOTES_PER_BAR
        return 1f / notesPerSecond
    }

    @JvmStatic fun calcNoteIndexRangeInSecondRange(secondRange: Double, currentTime: Float, bpm: Float,
                                                   offset: Float): IndexRange {
        val first = Math.ceil((currentTime - secondRange - offset) / calcSecondsPerNote(bpm)).toInt()
        val last = Math.floor((currentTime + secondRange - offset) / calcSecondsPerNote(bpm)).toInt()
        return IndexRange(first, last)
    }

    @JvmStatic fun calcFirstNoteX(currentTime: Float, bpm: Float, offset: Float, speed: Float): Float {
        val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
        return PositionConstants.PLAYER_JUDGE_X + (offset - currentTime) / calcSecondsPerNote(bpm) * spaceWidth
    }

    @JvmStatic fun calcNoteIndexRangeInEditor(notesSize: Int, translateYPx: Int, heightPx: Int,
                                              context: Context): IndexRange {
        val firstBarIndex = Math
                .floor(convertPx2Dp(translateYPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT).toInt()
        val lastBarIndex = Math.ceil(
                convertPx2Dp(translateYPx + heightPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT)
                .toInt()

        val firstNoteIndex = firstBarIndex * NumberConstants.NOTES_PER_BAR
        var lastNoteIndex = lastBarIndex * NumberConstants.NOTES_PER_BAR - 1
        if (lastNoteIndex >= notesSize) {
            lastNoteIndex = notesSize - 1
        }

        return IndexRange(firstNoteIndex, lastNoteIndex)
    }

    @JvmStatic fun calcBarIndexRangeInEditor(barNum: Int, translateYPx: Int, heightPx: Int,
                                             context: Context): IndexRange {
        val first = Math
                .floor(convertPx2Dp(translateYPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT).toInt()
        var last = Math.ceil(
                convertPx2Dp(translateYPx + heightPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT)
                .toInt()
        if (last >= barNum) {
            last = barNum - 1
        }
        return IndexRange(first, last)
    }

    @JvmStatic fun calcNoteIndexRangeInPlayer(notesSize: Int, speed: Float, widthPx: Int,
                                              firstNoteX: Float): IndexRange? {
        val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
        var first = Math.floor((-firstNoteX / spaceWidth).toDouble()).toInt() - 3
        val noteNum = Math.ceil(((widthPx - 1) / spaceWidth).toDouble()).toInt()
        var last = first + noteNum + 6

        if (first < 0) {
            first = 0
        }
        if (first >= notesSize) {
            return null
        }

        if (last < 0) {
            return null
        }
        if (last >= notesSize) {
            last = notesSize - 1
        }

        return IndexRange(first, last)
    }

    @JvmStatic fun calcEditorHeightPx(notesSize: Int, context: Context): Float {
        val heightDp = notesSize.toFloat() / NumberConstants.NOTES_PER_BAR * SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT
        return convertDp2Px(heightDp, context)
    }

    @JvmStatic fun calcBarWidth(widthPx: Int, context: Context): Float {
        return convertPx2Dp(widthPx, context) - PositionConstants.EDITOR_BAR_X * 2
    }

    @JvmStatic fun calcBarWidthPx(widthPx: Int, context: Context): Float {
        return convertDp2Px(calcBarWidth(widthPx, context), context)
    }

    @JvmStatic fun calcBarNum(notesSize: Int): Int {
        return Math.ceil(notesSize.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
    }
}
