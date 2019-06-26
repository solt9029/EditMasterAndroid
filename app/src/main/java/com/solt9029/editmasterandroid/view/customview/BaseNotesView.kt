package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.IdConstants

abstract class BaseNotesView : BaseSurfaceView {
    abstract val normalOutsidePx: Float
    abstract val normalInsidePx: Float
    abstract val bigOutsidePx: Float
    abstract val bigInsidePx: Float

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun drawNote(xPx: Float, yPx: Float, note: Int?, spaceWidthPx: Double, previousNote: Int?, nextNote: Int?,
                 canvas: Canvas) {
        val paint = Paint()

        val outsidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> normalOutsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> bigOutsidePx
            else -> return
        }
        val insidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> normalInsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> bigInsidePx
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

                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.color = color
                    canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)

                    paint.style = Paint.Style.STROKE
                    paint.color = ContextCompat.getColor(context, R.color.black)
                    canvas.drawLine(leftPx, topPx, rightPx, topPx, paint)
                    canvas.drawLine(leftPx, bottomPx, rightPx, bottomPx, paint)

                    return
                }

                // fill outside
                paint.style = Paint.Style.FILL_AND_STROKE
                paint.color = color
                canvas.drawCircle(xPx, yPx, outsidePx, paint)

                // stroke outside
                paint.style = Paint.Style.STROKE
                paint.color = ContextCompat.getColor(context, R.color.black)
                canvas.drawCircle(xPx, yPx, outsidePx, paint)

                return
            }
        }

        // fill outside
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = ContextCompat.getColor(context, R.color.white)
        canvas.drawCircle(xPx, yPx, outsidePx, paint)

        // stroke outside
        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawCircle(xPx, yPx, outsidePx, paint)

        // fill inside
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = color
        canvas.drawCircle(xPx, yPx, insidePx, paint)

        // stroke inside
        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawCircle(xPx, yPx, insidePx, paint)
    }
}