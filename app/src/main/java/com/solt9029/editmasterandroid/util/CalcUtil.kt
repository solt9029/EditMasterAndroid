package com.solt9029.editmasterandroid.util

import android.content.Context
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.PercentageConstants
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.constants.SizeConstants
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

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

    @JvmStatic fun calcFirstNoteXPx(currentTime: Float, bpm: Float, offset: Float, speed: Float,
                                    context: Context): Float {
        return convertDp2Px(calcFirstNoteX(currentTime, bpm, offset, speed), context)
    }

    @JvmStatic fun calcNoteIndexRangeInEditor(notesSize: Int, translateYPx: Int, heightPx: Int,
                                              context: Context): IndexRange {
        val firstBarIndex =
                floor(convertPx2Dp(translateYPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT).toInt()
        val lastBarIndex = ceil(convertPx2Dp(translateYPx + heightPx,
                context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT)
                .toInt()

        var firstNoteIndex = firstBarIndex * NumberConstants.NOTES_PER_BAR
        if (firstNoteIndex < 0) {
            firstNoteIndex = 0
        }
        var lastNoteIndex = lastBarIndex * NumberConstants.NOTES_PER_BAR - 1
        if (lastNoteIndex >= notesSize) {
            lastNoteIndex = notesSize - 1
        }

        return IndexRange(firstNoteIndex, lastNoteIndex)
    }

    @JvmStatic fun calcBarIndexRangeInEditor(barNum: Int, translateYPx: Int, heightPx: Int,
                                             context: Context): IndexRange {
        var first =
                floor(convertPx2Dp(translateYPx, context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT).toInt()
        if (first < 0) {
            first = 0
        }
        var last = ceil(convertPx2Dp(translateYPx + heightPx,
                context).toDouble() / SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT)
                .toInt()
        if (last >= barNum) {
            last = barNum - 1
        }
        return IndexRange(first, last)
    }

    @JvmStatic fun calcNoteIndexRangeInPlayer(notesSize: Int, speed: Float, widthPx: Int,
                                              firstNoteX: Float): IndexRange? {
        val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
        var first = floor((-firstNoteX / spaceWidth).toDouble()).toInt() - 3
        val noteNum = ceil(((widthPx - 1) / spaceWidth).toDouble()).toInt()
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
        return ceil(notesSize.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
    }

    @JvmStatic fun calcBarIndex(noteIndex: Int): Int {
        return floor(noteIndex.toFloat() / NumberConstants.NOTES_PER_BAR.toFloat()).toInt()
    }

    @JvmStatic fun calcCurrentTimeMarkPosition(widthPx: Int, bpm: Float, offset: Float, currentTime: Float,
                                               context: Context): Position {
        val barWidthPx = calcBarWidthPx(widthPx, context)
        val actualBarWidthPx = barWidthPx * (1 - PercentageConstants.EDITOR_BAR_START_LINE)
        val spaceWidthPx = actualBarWidthPx / NumberConstants.NOTES_PER_BAR

        val currentNoteIndexFloat: Float = (currentTime - offset) / calcSecondsPerNote(bpm)
        val currentBarIndexFloat: Float = currentNoteIndexFloat % NumberConstants.NOTES_PER_BAR
        val currentBarIndex: Int = floor(currentNoteIndexFloat / NumberConstants.NOTES_PER_BAR).toInt()

        val xPx = (currentBarIndexFloat * spaceWidthPx).toFloat()
        val yPx = currentBarIndex * convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context) +
                (convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context) - convertDp2Px(
                        SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, context)) / 2

        return Position(xPx, yPx)
    }

    @JvmStatic fun calcPointer(xPx: Float, yPx: Float, widthPx: Int, division: Int, context: Context): Pointer {
        val barWidthPx = calcBarWidthPx(widthPx, context)
        val barStartLineXPx = convertDp2Px(PositionConstants.EDITOR_BAR_X,
                context) + barWidthPx * PercentageConstants.EDITOR_BAR_START_LINE

        var divisionIndex: Int =
                round((xPx - barStartLineXPx) / ((barWidthPx * (1 - PercentageConstants.EDITOR_BAR_START_LINE)) / division)).toInt()
        if (divisionIndex < 0) {
            divisionIndex = 0
        }
        if (divisionIndex >= division) {
            divisionIndex = division - 1
        }

        val barIndex = floor(yPx / convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)).toInt()

        return Pointer(divisionIndex, barIndex)
    }
}
