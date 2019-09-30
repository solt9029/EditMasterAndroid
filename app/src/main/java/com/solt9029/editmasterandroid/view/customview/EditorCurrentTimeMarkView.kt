package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil

class EditorCurrentTimeMarkView : BaseSurfaceView, SurfaceHolder.Callback {
    private val editorCurrentTimeMarkWidth =
            CalcUtil.convertDp2Px(SizeConstants.EDITOR_CURRENT_TIME_MARK_WIDTH, context)
    private val editorBarInsideHeight = CalcUtil.convertDp2Px(SizeConstants.EDITOR_BAR_INSIDE_HEIGHT, context)

    private var bpm: Float = 0f
    private var offset: Float = 0f
    private var currentTime: Float = 0f
    private var translateYPx: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    fun setBpm(bpm: Float) {
        this.bpm = bpm
        draw()
    }

    fun setOffset(offset: Float) {
        this.offset = offset
        draw()
    }

    fun setCurrentTime(currentTime: Float) {
        this.currentTime = currentTime
        draw()
    }

    fun setTranslateYPx(translateYPx: Int) {
        this.translateYPx = translateYPx
        draw()
    }

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawCurrentTimeMark(canvas)

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawCurrentTimeMark(canvas: Canvas) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.purple)
        paint.style = Paint.Style.FILL

        val position = CalcUtil.calcCurrentTimeMarkPosition(width, bpm, offset, currentTime, context)

        val leftPx = position.xPx - editorCurrentTimeMarkWidth / 2
        val topPx = position.yPx - 2 - translateYPx
        val rightPx = leftPx + editorCurrentTimeMarkWidth
        val bottomPx = topPx + editorBarInsideHeight + 4
        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)
    }
}
