package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.solt9029.editmasterandroid.constants.*
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.IndexRange

class EditorNotesView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

    private val editorBarXPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, context)
    private val editorBarOutsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)

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
            val note: Int = notes!![i]
            if (note == IdConstants.Note.SPACE) {
                continue
            }
            val c: Int = i % NumberConstants.NOTES_PER_BAR
            val l: Int = Math.floor(i.toDouble() / NumberConstants.NOTES_PER_BAR).toInt()
            val x = barStartLineXPx + spaceWidthPx * c
            val y = editorBarOutsideHeightPx * (l + 0.5)

            val previousNote = if (i > 0) notes!![i - 1] else IdConstants.Note.SPACE
            val nextNote = if (i < notes!!.size - 1) notes!![i + 1] else IdConstants.Note.SPACE

            // draw notes here
        }

        holder.unlockCanvasAndPost(canvas)
    }
}

