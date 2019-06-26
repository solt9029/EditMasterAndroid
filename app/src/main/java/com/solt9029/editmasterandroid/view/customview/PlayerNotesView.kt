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

class PlayerNotesView : BaseNotesView {
    private val playerBarStartLineWidthPx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BAR_START_LINE_WIDTH, context)
    override val normalOutsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_OUTSIDE, context)
    override val normalInsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_NORMAL_INSIDE, context)
    override val bigOutsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BIG_OUTSIDE, context)
    override val bigInsidePx = CalcUtil.convertDp2Px(SizeConstants.PLAYER_BIG_INSIDE, context)

    private var notes: List<Int>? = null
    private var currentTime: Float = 0f
    private var bpm: Float = 0f
    private var offset: Float = 0f
    private var speed: Float = 0f
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
        val spaceWidth = speed * PercentageConstants.PLAYER_SPEED_TO_SPACE_WIDTH
        val firstBarStartLineIndex = range.first - (range.first % NumberConstants.NOTES_PER_BAR)

        for (i in firstBarStartLineIndex..range.last step NumberConstants.NOTES_PER_BAR) {
            val xPx = CalcUtil.convertDp2Px(firstNoteX + i * spaceWidth, context)
            drawBarStartLine(xPx, canvas)
        }
    }

    private fun drawBarStartLine(xPx: Float, canvas: Canvas) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.white)
        paint.style = Paint.Style.FILL

        val leftPx = xPx - playerBarStartLineWidthPx / 2
        val topPx = 0f
        val rightPx = leftPx + playerBarStartLineWidthPx
        val bottomPx = topPx + height

        canvas.drawRect(leftPx, topPx, rightPx, bottomPx, paint)
    }

    private fun drawNotes(firstNoteX: Float, range: IndexRange, canvas: Canvas) {
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

            drawNote(xPx, yPx, note, spaceWidthPx, previousNote, nextNote, canvas)
        }
    }
}
