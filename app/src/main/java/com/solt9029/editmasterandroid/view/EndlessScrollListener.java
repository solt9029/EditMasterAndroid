package com.solt9029.editmasterandroid.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager manager;
    public static final int VISIBLE_THRESHOLD = 1;

    EndlessScrollListener(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        super.onScrolled(view, dx, dy);

        // prevent onScrolled from being called when error happens. onScrolled is called when item count changes!
        if (dy <= 0) {
            return;
        }

        int visibleItemCount = view.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisibleItem = manager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}