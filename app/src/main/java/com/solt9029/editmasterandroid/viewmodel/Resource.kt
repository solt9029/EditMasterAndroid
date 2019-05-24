package com.solt9029.editmasterandroid.viewmodel

class Resource<T>() {
    var data: T? = null
    var isLoading = false
    var error: Throwable? = null

    private constructor(data: T?, isLoading: Boolean, error: Throwable?) : this() {
        this.data = data
        this.isLoading = isLoading
        this.error = error
    }

    companion object {

        fun <T> startLoading(data: T?): Resource<T> {
            return Resource(data, true, null)
        }

        fun <T> finishLoadingSuccess(data: T?): Resource<T> {
            return Resource(data, false, null)
        }

        fun <T> finishLoadingFailure(error: Throwable?): Resource<T> {
            return Resource(null, false, error)
        }
    }
}
