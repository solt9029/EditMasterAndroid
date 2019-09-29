package com.solt9029.editmasterandroid.constants

class IdConstants {
    // note ids correspond to the radio button group item positions
    object Note {
        const val SPACE = 0
        const val DON = 1
        const val KA = 2
        const val BIGDON = 3
        const val BIGKA = 4
        const val RENDA = 5
        const val BIGRENDA = 6
        const val BALLOON = 7
        const val COPY = 8
        const val PASTE = 9
        @JvmField val RADIO_NOTE_MAPPING =
                intArrayOf(DON, KA, BIGDON, BIGKA, RENDA, BIGRENDA, BALLOON, SPACE, COPY, PASTE)
    }

    enum class State {
        FRESH, GOOD, OK, BAD
    }

    enum class DialogType {
        LOADING, SUCCESS, FAILURE, VALIDATION_ERROR
    }
}
