package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.PercentageConstants
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil

class EditorBarsView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

    private val editorBarOutsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT, context)
    private val editorBarInsideHeightPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, context)
    private val editorBarXPx = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X, context)
    private val editorBeatLineWidthPx = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BEAT_LINE_WIDTH, context)

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
        val canvas = holder.lockCanvas() ?: return
        notes ?: return

        drawBars(notes, canvas)

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawBars(notes: List<Int>?, canvas: Canvas) {
        val notesSize = notes?.size ?: return

        canvas.drawColor(resources.getColor(R.color.colorBackground))

        val paint = Paint()
        paint.style = Style.FILL

        val barNum = CalcUtil.calcBarNum(notesSize)
        val range = CalcUtil.calcBarIndexRangeInEditor(barNum, translateYPx, height, context)
        for (i in range.first..range.last) {
            val yPx =
                    i * CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT.toFloat(), context) - translateYPx
            drawBar(yPx, canvas, paint)
        }
    }

    private fun drawBar(yPx: Float, canvas: Canvas, paint: Paint) {
        val barWidthPx = CalcUtil.calcBarWidthPx(width, context);

        paint.color = resources.getColor(R.color.gray)
        val leftPx: Float = editorBarXPx
        val topPx: Float = yPx + (editorBarOutsideHeightPx - editorBarInsideHeightPx) / 2
        val rightPx: Float = leftPx + barWidthPx
        val bottomPx: Float = topPx + editorBarInsideHeightPx
        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)

        paint.color = resources.getColor(R.color.white)
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

