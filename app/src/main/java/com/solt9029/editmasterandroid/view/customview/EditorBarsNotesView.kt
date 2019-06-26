package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.*
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.IndexRange
import kotlin.math.floor

class EditorBarsNotesView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

    private val editorBarOutsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)
    private val editorBarInsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, context)
    private val editorBarXPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, context)
    private val editorBeatLineWidthPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BEAT_LINE_WIDTH, context)
    private val editorNormalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_NORMAL_OUTSIDE, context)
    private val editorNormalInsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_NORMAL_INSIDE, context)
    private val editorBigOutsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BIG_OUTSIDE, context)
    private val editorBigInsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BIG_INSIDE, context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setTranslateYPx(translateYPx: Int) {
        this.translateYPx = translateYPx
        draw()
    }

    fun setNotes(notes: List<Int>?) {
        this.notes = notes
        draw()
    }

    override fun draw() {
        notes ?: return
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
        val range: IndexRange = CalcUtil.calcNoteIndexRangeInEditor(notes!!.size, translateYPx, height, context)

        for (i in range.last downTo range.first) {
            val note: Int? = notes!![i]
            if (note == IdConstants.Note.SPACE) {
                continue
            }
            val c: Int = i % NumberConstants.NOTES_PER_BAR
            val l: Int = floor(i.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
            val xPx: Float = (barStartLineXPx + spaceWidthPx * c).toFloat()
            val yPx: Float = (editorBarOutsideHeightPx * (l + 0.5) - translateYPx).toFloat()

            val previousNote: Int? = if (i > 0) notes!![i - 1] else IdConstants.Note.SPACE
            val nextNote: Int? = if (i < notes!!.size - 1) notes!![i + 1] else IdConstants.Note.SPACE

            // draw notes here
            drawNote(xPx, yPx, note, spaceWidthPx, previousNote, nextNote, canvas)
        }
    }

    private fun drawNote(xPx: Float, yPx: Float, note: Int?, spaceWidthPx: Double, previousNote: Int?, nextNote: Int?,
                         canvas: Canvas) {
        val paint = Paint()

        val outsidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> editorNormalOutsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> editorBigOutsidePx
            else -> return
        }
        val insidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> editorNormalInsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> editorBigInsidePx
            else -> return
        }
        val color: Int = when (note) {
            IdConstants.Note.DON, IdConstants.Note.BIGDON, IdConstants.Note.BALLOON ->
                ContextCompat.getColor(context, R.color.red)
            IdConstants.Note.KA, IdConstants.Note.BIGKA -> ContextCompat.getColor(context, R.color.blue)
            IdConstants.Note.RENDA, IdConstants.Note.BIGRENDA -> ContextCompat.getColor(context, R.color.yellow)
            else -> return
        }

        if (note == IdConstants.Note.BALLOON || note == IdConstants.Note.RENDA || note == IdConstants.Note.BIGRENDA) {
            if (note == previousNote) {
                if (note == nextNote) {
                    val leftPx: Float = (xPx - spaceWidthPx - 1).toFloat()
                    val topPx: Float = yPx - outsidePx
                    val rightPx: Float = (leftPx + spaceWidthPx * 2 + 2).toFloat()
                    val bottomPx: Float = topPx + outsidePx * 2

                    paint.style = Style.FILL_AND_STROKE
                    paint.color = color
                    canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)

                    paint.style = Style.STROKE
                    paint.color = ContextCompat.getColor(context, R.color.black)
                    canvas.drawLine(leftPx, topPx, rightPx, topPx, paint)
                    canvas.drawLine(leftPx, bottomPx, rightPx, bottomPx, paint)

                    return
                }

                // fill outside
                paint.style = Style.FILL_AND_STROKE
                paint.color = color
                canvas.drawCircle(xPx, yPx, outsidePx, paint)

                // stroke outside
                paint.style = Style.STROKE
                paint.color = ContextCompat.getColor(context, R.color.black)
                canvas.drawCircle(xPx, yPx, outsidePx, paint)

                return
            }
        }
        
        // fill outside
        paint.style = Style.FILL_AND_STROKE
        paint.color = ContextCompat.getColor(context, R.color.white)
        canvas.drawCircle(xPx, yPx, outsidePx, paint)

        // stroke outside
        paint.style = Style.STROKE
        paint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawCircle(xPx, yPx, outsidePx, paint)

        // fill inside
        paint.style = Style.FILL_AND_STROKE
        paint.color = color
        canvas.drawCircle(xPx, yPx, insidePx, paint)

        // stroke inside
        paint.style = Style.STROKE
        paint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawCircle(xPx, yPx, insidePx, paint)
    }

    private fun drawBars(canvas: Canvas) {
        val notesSize = notes!!.size

        val barNum = CalcUtil.calcBarNum(notesSize)
        val range = CalcUtil.calcBarIndexRangeInEditor(barNum, translateYPx, height, context)
        for (i in range.first..range.last) {
            val yPx =
                    i * CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT.toFloat(), context) - translateYPx
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

