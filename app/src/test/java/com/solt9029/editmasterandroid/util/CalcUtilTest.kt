package com.solt9029.editmasterandroid.util

import com.solt9029.editmasterandroid.constants.SecondConstants
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Assert.assertThat
import org.junit.Test


class CalcUtilTest {
    @Test
    fun calcSecondsPerNote() {
        val actual = CalcUtil.calcSecondsPerNote(240f)
        val expected = 1f / 96f
        assertThat(actual, `is`(expected))
    }

    @Test
    fun calcNoteIndexRangeInSecondRange() {
        val actual = CalcUtil.calcNoteIndexRangeInSecondRange(SecondConstants.RANGE_AUTO, 10f, 150f, 1f)
        val expected = IndexRange(539, 541)
        assertThat(actual, samePropertyValuesAs(expected))
    }
}
