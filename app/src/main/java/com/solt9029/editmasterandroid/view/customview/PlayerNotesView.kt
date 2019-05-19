package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil

class PlayerNotesView : BaseSurfaceView {
    private var currentTime: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setCurrentTime(currentTime: Float) {
        this.currentTime = currentTime
        draw()
    }

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(Color.WHITE)
        val paint = Paint()
        paint.style = Paint.Style.FILL

        val radiusPx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_OUTSIDE.toFloat(), context)
        canvas.drawCircle(currentTime * 10, (height / 2.0).toFloat(), radiusPx, paint)

        holder.unlockCanvasAndPost(canvas)
    }
}
