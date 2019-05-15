package com.solt9029.editmasterandroid.util;

import com.solt9029.editmasterandroid.constant.SecondConstants;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;


public class CalcUtilTest {
    @Test
    public void calcSecondsPerNote() {
        float actual = CalcUtil.calcSecondsPerNote(240);
        float expected = 1f / 96f;
        assertThat(actual, is(expected));
    }

    @Test
    public void calcNoteIndexRangeInSecondRange() {
        NoteIndexRange<Double> actual = CalcUtil.calcNoteIndexRangeInSecondRange(SecondConstants.Range.AUTO, 10, 150, 1);
        NoteIndexRange<Double> expected = new NoteIndexRange<>(539d, 541d);
        assertThat(actual, samePropertyValuesAs(expected));
    }
}
