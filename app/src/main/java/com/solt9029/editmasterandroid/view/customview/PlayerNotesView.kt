package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.IdConstants
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.PercentageConstants
import com.solt9029.editmasterandroid.constants.SizeConstants
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.IndexRange

class PlayerNotesView : BaseSurfaceView {
    private val playerBarStartLineWidthPx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BAR_START_LINE_WIDTH, context)
    private val playerNormalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_OUTSIDE, context)
    private val playerNormalInsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_INSIDE, context)
    private val playerBigOutsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BIG_OUTSIDE, context)
    private val playerBigInsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BIG_INSIDE, context)

    private var currentTime: Float = 0f
    private var bpm: Float = 0f
    private var offset: Float = 0f
    private var speed: Float = 0f
    private var notes: List<Int>? = null
    private var states: List<IdConstants.State>? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setCurrentTime(currentTime: Float) {
        this.currentTime = currentTime
        draw()
    }

    fun setBpm(bpm: Float) {
        this.bpm = bpm
        draw()
    }

    fun setOffset(offset: Float) {
        this.offset = offset
        draw()
    }

    fun setSpeed(speed: Float) {
        this.speed = speed
        draw()
    }

    fun setNotes(notes: List<Int>?) {
        this.notes = notes
        draw()
    }

    fun setStates(states: List<IdConstants.State>?) {
        this.states = states
        draw()
    }

    override fun draw() {
        notes ?: return
        val firstNoteX = CalcUtil.calcFirstNoteX(currentTime, bpm, offset, speed)
        val range = CalcUtil.calcNoteIndexRangeInPlayer(notes!!.size, speed, width, firstNoteX)
        range ?: return
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(ContextCompat.getColor(context, R.color.colorBackground))
        drawBarStartLines(firstNoteX, range, canvas)
        drawNotes(firstNoteX, range, canvas)

        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawBarStartLines(firstNoteX: Float, range: IndexRange, canvas: Canvas) {
        val paint = Paint()

        val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
        val firstBarStartLineIndex = range.first - (range.first % NumberConstants.NOTES_PER_BAR)

        for (i in firstBarStartLineIndex..range.last step NumberConstants.NOTES_PER_BAR) {
            val xPx = CalcUtil.convertDp2Px(firstNoteX + i * spaceWidth, context)
            drawBarStartLine(xPx, canvas, paint)
        }
    }

    private fun drawBarStartLine(xPx: Float, canvas: Canvas, paint: Paint) {
        paint.color = ContextCompat.getColor(context, R.color.white)
        paint.style = Paint.Style.FILL

        val leftPx = xPx - playerBarStartLineWidthPx / 2
        val topPx = 0f
        val rightPx = leftPx + playerBarStartLineWidthPx
        val bottomPx = topPx + height

        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)
    }

    private fun drawNotes(firstNoteX: Float, range: IndexRange, canvas: Canvas) {
        val paint = Paint()

        for (i in range.last downTo range.first) {
            val note: Int? = notes!![i]
            val state = states!![i]
            if (note == IdConstants.Note.SPACE || state != IdConstants.State.FRESH) {
                continue
            }

            val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
            val spaceWidthPx: Double = CalcUtil.convertDp2Px(spaceWidth, context).toDouble()
            val xPx = CalcUtil.convertDp2Px(firstNoteX + i * spaceWidth, context)
            val yPx = (height / 2).toFloat()
            val previousNote: Int? = if (i > 0) notes!![i - 1] else IdConstants.Note.SPACE
            val nextNote: Int? = if (i < notes!!.size - 1) notes!![i + 1] else IdConstants.Note.SPACE

            drawNote(xPx, yPx, note, spaceWidthPx, previousNote, nextNote, canvas, paint)
        }
    }

    private fun drawNote(xPx: Float, yPx: Float, note: Int?, spaceWidthPx: Double, previousNote: Int?, nextNote: Int?,
                         canvas: Canvas, paint: Paint) {
        val outsidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> playerNormalOutsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> playerBigOutsidePx
            else -> return
        }
        val insidePx: Float = when (note) {
            IdConstants.Note.DON, IdConstants.Note.KA, IdConstants.Note.BALLOON, IdConstants.Note.RENDA -> playerNormalInsidePx
            IdConstants.Note.BIGDON, IdConstants.Note.BIGKA, IdConstants.Note.BIGRENDA -> playerBigInsidePx
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
