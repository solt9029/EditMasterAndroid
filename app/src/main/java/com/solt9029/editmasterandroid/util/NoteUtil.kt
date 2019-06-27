package com.solt9029.editmasterandroid.util

import com.solt9029.editmasterandroid.constants.IdConstants

object NoteUtil {
    @JvmStatic fun hasState(note: Int): Boolean {
        return note == IdConstants.Note.DON ||
                note == IdConstants.Note.KA ||
                note == IdConstants.Note.BIGDON ||
                note == IdConstants.Note.BIGKA
    }

    @JvmStatic fun isDon(note: Int): Boolean {
        return note == IdConstants.Note.DON ||
                note == IdConstants.Note.BIGDON ||
                note == IdConstants.Note.RENDA ||
                note == IdConstants.Note.BIGRENDA ||
                note == IdConstants.Note.BALLOON
    }

    @JvmStatic fun isKa(note: Int): Boolean {
        return note == IdConstants.Note.KA ||
                note == IdConstants.Note.BIGKA ||
                note == IdConstants.Note.RENDA ||
                note == IdConstants.Note.BIGRENDA
    }
}
