package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.*
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.IndexRange
import kotlin.math.floor

class EditorBarsNotesView : BaseNotesView {
    private var translateYPx: Int = 0
    private var notes: List<Int> = listOf()

    private val editorBarOutsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)
    private val editorBarInsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, context)
    private val editorBarXPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, context)
    private val editorBeatLineWidthPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BEAT_LINE_WIDTH, context)
    override val normalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_NORMAL_OUTSIDE, context)
    override val normalInsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_NORMAL_INSIDE, context)
    override val bigOutsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BIG_OUTSIDE, context)
    override val bigInsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BIG_INSIDE, context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setTranslateYPx(translateYPx: Int) {
        this.translateYPx = translateYPx
        draw()
    }

    fun setNotes(notes: List<Int>) {
        this.notes = notes
        draw()
    }

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(ContextCompat.getColor(context, R.color.colorBackground))
        drawBars(canvas)
        drawNotes(canvas)

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawNotes(canvas: Canvas) {
        val barWidthPx = CalcUtil.calcBarWidthPx(width, context)
        val actualBarWidthPx = barWidthPx * (1 - PercentageConstants.EDITOR_BAR_START_LINE)
        val spaceWidthPx = actualBarWidthPx / NumberConstants.NOTES_PER_BAR
        val barStartLineXPx = editorBarXPx + barWidthPx * PercentageConstants.EDITOR_BAR_START_LINE
        val range: IndexRange = CalcUtil.calcNoteIndexRangeInEditor(notes.size, translateYPx, height, context)

        for (i in range.last downTo range.first) {
            val note: Int? = notes[i]
            if (note == IdConstants.Note.SPACE) {
                continue
            }
            val c: Int = i % NumberConstants.NOTES_PER_BAR
            val l: Int = floor(i.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
            val xPx: Float = (barStartLineXPx + spaceWidthPx * c).toFloat()
            val yPx: Float = (editorBarOutsideHeightPx * (l + 0.5) - translateYPx).toFloat()

            val previousNote: Int? = if (i > 0) notes[i - 1] else IdConstants.Note.SPACE
            val nextNote: Int? = if (i < notes.size - 1) notes[i + 1] else IdConstants.Note.SPACE

            // draw notes here
            drawNote(xPx, yPx, note, spaceWidthPx, previousNote, nextNote, canvas)
        }
    }

    private fun drawBars(canvas: Canvas) {
        val notesSize = notes.size

        val barNum = CalcUtil.calcBarNum(notesSize)
        val range = CalcUtil.calcBarIndexRangeInEditor(barNum, translateYPx, height, context)
        for (i in range.first..range.last) {
            val yPx = i * CalcUtil.convertDp2Px(
                    SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT.toFloat(), context) - translateYPx
            drawBar(yPx, canvas)
        }
    }

    private fun drawBar(yPx: Float, canvas: Canvas) {
        val barWidthPx = CalcUtil.calcBarWidthPx(width, context)

        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.gray)
        paint.style = Style.FILL

        val leftPx: Float = editorBarXPx
        val topPx: Float = yPx + (editorBarOutsideHeightPx - editorBarInsideHeightPx) / 2
        val rightPx: Float = leftPx + barWidthPx
        val bottomPx: Float = topPx + editorBarInsideHeightPx
        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)

        paint.color = ContextCompat.getColor(context, R.color.white)
        for (i in 0 until NumberConstants.BEAT) {
            val beatLineLeftPx =
                    leftPx + barWidthPx * (PercentageConstants.EDITOR_BAR_START_LINE +
                            ((1 - PercentageConstants.EDITOR_BAR_START_LINE) * i) / NumberConstants.BEAT) -
                            editorBeatLineWidthPx / 2
            canvas.drawRect(beatLineLeftPx.toFloat(), topPx - 1, (beatLineLeftPx + editorBeatLineWidthPx).toFloat(),
                    bottomPx + 1, paint)
        }
    }
}

