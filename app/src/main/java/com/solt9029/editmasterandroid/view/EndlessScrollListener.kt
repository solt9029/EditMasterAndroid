package com.solt9029.editmasterandroid.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener protected constructor(
        private val manager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(view, dx, dy)

        // prevent onScrolled from being called when error happens. onScrolled is called when item count changes!
        if (dy <= 0) {
            return
        }

        val visibleItemCount = view.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItem = manager.findFirstVisibleItemPosition()

        if (totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }
}