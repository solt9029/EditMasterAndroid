package com.solt9029.editmasterandroid.constants

class IdConstants {
    // note ids correspond to the radio button group item positions
    object Note {
        const val DON = 0
        const val KA = 1
        const val BIGDON = 2
        const val BIGKA = 3
        const val RENDA = 4
        const val BIGRENDA = 5
        const val BALLOON = 6
        const val SPACE = 7
        const val COPY = 8
        const val PASTE = 9
    }

    enum class State {
        FRESH, GOOD, OK, BAD
    }
}
