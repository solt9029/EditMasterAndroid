package com.solt9029.editmasterandroid.viewmodel

import android.content.Context
import com.solt9029.editmasterandroid.constants.PositionConstants
import com.solt9029.editmasterandroid.util.CalcUtil

class ShotEffect(val note: Int, val widthPx: Float, val heightPx: Float, val context: Context) {
    var xPx = CalcUtil.convertDp2Px(PositionConstants.PLAYER_JUDGE_X, context)
    var yPx = heightPx / 2
    var g: Float = 1f
    var limit = 5

    fun update() {
        xPx += widthPx / 100
        yPx -= heightPx / 10 / g
        g += 0.1f
        limit--
    }
}