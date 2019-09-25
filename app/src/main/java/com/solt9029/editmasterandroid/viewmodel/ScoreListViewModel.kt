package com.solt9029.editmasterandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solt9029.editmasterandroid.entity.Score
import com.solt9029.editmasterandroid.repository.ScoreRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ScoreListViewModel @Inject constructor(
        private val repository: ScoreRepository
) : ViewModel() {

    var resource = MutableLiveData(Resource<List<Score>>())
    var isRefreshing = MutableLiveData(false)
    var navigateToScoreActivity = LiveEvent<Int>()
    var keyword = MutableLiveData<String?>()
    private val compositeDisposable = CompositeDisposable()

    init {
        onLoad()
    }

    public override fun onCleared() {
        compositeDisposable.clear()
    }

    fun onRefresh() {
        Timber.d("onRefresh")
        compositeDisposable.clear()

        isRefreshing.value = true
        resource.value = Resource.startLoading(resource.value?.data)
        val disposable = fetchScoreTimeline().subscribe(
                { result ->
                    isRefreshing.value = false
                    resource.value = Resource.finishLoadingSuccess(result)
                    Timber.d("refresh success")
                },
                { error ->
                    isRefreshing.value = false
                    resource.value = Resource.finishLoadingFailure(error)
                    Timber.e("refresh failure")
                    Timber.e(error.message)
                }
        )
        compositeDisposable.add(disposable)
    }

    fun onLoad() {
        Timber.d("onLoad")
        compositeDisposable.clear()

        resource.value = Resource.startLoading(null)
        val disposable = fetchScoreTimeline().subscribe(
                { result ->
                    resource.value = Resource.finishLoadingSuccess(result)
                    Timber.d("load success")
                },
                { error ->
                    resource.value = Resource.finishLoadingFailure(error)
                    Timber.e("load failure")
                    Timber.e(error.message)
                }
        )
        compositeDisposable.add(disposable)
    }

    fun onLoadMore() {
        if (resource.value?.isLoading == true) {
            return
        }

        if (maxId != null && maxId!! < 1) {
            return
        }

        Timber.d("onLoadMore")

        resource.value = Resource.startLoading(resource.value?.data)
        val disposable = fetchScoreTimeline(maxId).subscribe(
                { result ->
                    val newData = ArrayList<Score>()
                    resource.value?.data?.let { data ->
                        newData.addAll(data)
                    }
                    newData.addAll(result)
                    resource.setValue(Resource.finishLoadingSuccess(newData))
                    Timber.d("load more success")
                },
                { error ->
                    resource.value = Resource.finishLoadingFailure(error)
                    Timber.e("load more failure")
                    Timber.e(error.message)
                }
        )
        compositeDisposable.add(disposable)
    }

    private fun fetchScoreTimeline(maxId: Int? = null): Single<List<Score>> {
        return repository.getScoreTimeline(null, keyword.value, maxId, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private val maxId: Int?
        get() {
            resource.value?.data?.let { data ->
                data[data.size - 1].id?.let { id ->
                    return id - 1
                }
            }
            return null
        }

    fun navigateToScoreActivity(id: Int) {
        navigateToScoreActivity.call(id)
    }
}