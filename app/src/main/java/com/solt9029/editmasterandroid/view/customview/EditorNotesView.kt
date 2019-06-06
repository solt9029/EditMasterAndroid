package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.*
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.IndexRange

class EditorNotesView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

    private val editorBarXPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, context)
    private val editorBarOutsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)
    private val editorNormalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_NORMAL_OUTSIDE, context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    fun setTranslateYPx(translateYPx: Int) {
        this.translateYPx = translateYPx
        draw()
    }

    fun setNotes(notes: List<Int>?) {
        this.notes = notes
        draw()
    }

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return
        notes ?: return

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        val paint = Paint()

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
            val l: Int = Math.floor(i.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
            val xPx: Float = (barStartLineXPx + spaceWidthPx * c).toFloat()
            val yPx: Float = (editorBarOutsideHeightPx * (l + 0.5) - translateYPx).toFloat()

            val previousNote: Int? = if (i > 0) notes!![i - 1] else IdConstants.Note.SPACE
            val nextNote: Int? = if (i < notes!!.size - 1) notes!![i + 1] else IdConstants.Note.SPACE

            // draw notes here
            drawNote(xPx, yPx, note, spaceWidthPx, previousNote, nextNote, canvas, paint)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawNote(xPx: Float, yPx: Float, note: Int?, spaceWidthPx: Double, previousNote: Int?, nextNote: Int?,
                         canvas: Canvas, paint: Paint) {
        when (note) {
            IdConstants.Note.SPACE -> return
            else -> {
            }
        }

        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = ContextCompat.getColor(context, R.color.white)
        canvas.drawCircle(xPx, yPx, editorNormalOutsidePx, paint)

    }
}

