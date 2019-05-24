package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder

class EditorNotesView : BaseSurfaceView, SurfaceHolder.Callback {
    private var translateYPx: Int = 0
    private var notes: List<Int>? = null

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

    fun setNotes(notes: List<Int>) {
        this.notes = notes
        draw()
    }

    override fun draw() {
        val canvas = holder.lockCanvas() ?: return

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        val paint = Paint()
        paint.setARGB(255, 255, 0, 0)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(300f, 300f, 10f, paint)

        holder.unlockCanvasAndPost(canvas)
    }
}

