package com.solt9029.editmasterandroid.viewmodel;

public class Field<T> {
    private T value;
    private String error = null;

    public Field(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
