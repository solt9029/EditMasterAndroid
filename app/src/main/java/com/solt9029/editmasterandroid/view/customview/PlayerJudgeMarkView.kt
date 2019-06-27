package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil

class PlayerJudgeMarkView : BaseSurfaceView {
    private val playerJudgeXPx = CalcUtil.convertDp2Px(PositionConstants.PLAYER_JUDGE_X, context)
    private val playerNormalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_OUTSIDE, context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(ContextCompat.getColor(context, R.color.colorBackground))
        drawJudgeMark(canvas)

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawJudgeMark(canvas: Canvas) {
        val paint = Paint()

        paint.color = ContextCompat.getColor(context, R.color.white)
        paint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawCircle(playerJudgeXPx, (height / 2).toFloat(), playerNormalOutsidePx, paint)

        paint.color = ContextCompat.getColor(context, R.color.colorBackground)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(playerJudgeXPx, (height / 2).toFloat(), playerNormalOutsidePx - 3, paint)
    }
}