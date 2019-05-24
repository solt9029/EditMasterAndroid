package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

abstract class BaseSurfaceView : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        isFocusable = true
        holder.addCallback(this)
        requestFocus()
        setZOrderOnTop(true)
    }

    abstract fun draw()

    override fun surfaceCreated(holder: SurfaceHolder) {
        draw()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        draw()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(widthSize, heightSize)
    }
}

