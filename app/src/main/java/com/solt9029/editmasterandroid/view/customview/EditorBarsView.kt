package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil
import timber.log.Timber

class EditorBarsView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

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
        val notesSize = notes?.size ?: return

        canvas.drawColor(resources.getColor(R.color.colorBackground))

        val paint = Paint()
        paint.style = Style.FILL
        paint.color = resources.getColor(R.color.gray)

        val barNum = CalcUtil.calcBarNum(notesSize)
        Timber.d("barNum: $barNum")
        for (i in 0 until barNum) {
            val yPx =
                    i * CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT.toFloat(), context) - translateYPx
            drawBar(yPx, canvas, paint)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    fun drawBar(yPx: Float, canvas: Canvas, paint: Paint) {
        val leftPx: Float = CalcUtil.convertDp2Px(PositionConstants.EDITOR_BAR_X.toFloat(), context)
        val topPx: Float = yPx + CalcUtil.convertDp2Px(
                (SizeConstants.EDITOR_BAR_OUTSIDE_HEIGHT - SizeConstants.EDITOR_BAR_INSIDE_HEIGHT).toFloat() / 2,
                context)
        val rightPx: Float = leftPx + CalcUtil.calcBarWidthPx(width, context)
        val bottomPx: Float = topPx + CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT.toFloat(), context)
        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)
    }

}

