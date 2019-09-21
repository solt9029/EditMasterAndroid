package com.solt9029.editmasterandroid.viewmodel

import android.view.GestureDetector
import android.view.MotionEvent

abstract class GestureListener : GestureDetector.SimpleOnGestureListener() {
    var widthPx: Int = 0

    abstract override fun onSingleTapUp(event: MotionEvent?): Boolean
}