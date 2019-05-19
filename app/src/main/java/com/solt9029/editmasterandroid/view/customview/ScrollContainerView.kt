package com.solt9029.editmasterandroid.view.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import timber.log.Timber

class ScrollContainerView : ScrollView {
    private var listener: OnScrollChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setOnScrollChangeListener(listener: OnScrollChangeListener) {
        this.listener = listener
    }

    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(x, y, oldX, oldY)
        listener?.onScrollChange(x, y, oldX, oldY)
        Timber.d("ScrollX: $x px")
        Timber.d("ScrollY: $y px")
    }

    interface OnScrollChangeListener {
        fun onScrollChange(x: Int, y: Int, oldX: Int, oldY: Int)
    }
}