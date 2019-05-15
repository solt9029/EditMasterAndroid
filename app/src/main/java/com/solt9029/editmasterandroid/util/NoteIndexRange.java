package com.solt9029.editmasterandroid.util;

public class NoteIndexRange<T> {
    private T first;
    private T last;

    public NoteIndexRange(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getLast() {
        return last;
    }

    public void setLast(T last) {
        this.last = last;
    }
}
