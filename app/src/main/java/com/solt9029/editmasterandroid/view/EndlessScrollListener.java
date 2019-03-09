package com.solt9029.editmasterandroid.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager manager;

    EndlessScrollListener(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        super.onScrolled(view, dx, dy);

        // prevent onScrolled being called when error happens, note that onScrolled is called when item count changes!
        if (dy <= 0) {
            return;
        }

        int visibleItemCount = view.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisibleItem = manager.findFirstVisibleItemPosition();
        int visibleThreshold = 1;

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}