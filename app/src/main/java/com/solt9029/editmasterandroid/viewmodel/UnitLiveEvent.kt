package com.solt9029.editmasterandroid.viewmodel

import androidx.annotation.MainThread

class UnitLiveEvent : LiveEvent<Unit>() {

    @MainThread
    fun call() {
        super.call(Unit)
    }

    @MainThread
    @Deprecated(
            message = "use call()",
            replaceWith = ReplaceWith("call()"),
            level = DeprecationLevel.HIDDEN
    )
    override fun call(t: Unit?) {
        super.call(t)
    }

}
