package com.solt9029.editmasterandroid.model;

public class Resource<T> {
    public T data = null;
    public boolean isLoading = false;
    public Throwable error = null;

    public Resource() {

    }

    private Resource(T data, boolean isLoading, Throwable error) {
        this.data = data;
        this.isLoading = isLoading;
        this.error = error;
    }

    public static <T> Resource<T> startLoading(T data) {
        return new Resource<>(data, true, null);
    }

    public static <T> Resource<T> finishLoadingSuccess(T data) {
        return new Resource<>(data, false, null);
    }

    public static <T> Resource<T> finishLoadingFailure(Throwable error) {
        return new Resource<>(null, false, error);
    }
}
