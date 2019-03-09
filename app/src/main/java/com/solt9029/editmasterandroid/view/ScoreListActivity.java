package com.solt9029.editmasterandroid.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.ActivityScoreListBinding;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;

import java.util.List;

public class ScoreListActivity extends AppCompatActivity {
    private ActivityScoreListBinding binding;
    private ScoreListViewModel viewModel;
    private ScoreListController controller = new ScoreListController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        viewModel = ViewModelProviders.of(this).get(ScoreListViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_score_list);
        binding.setAdapter(controller.getAdapter());
        binding.setViewModel(viewModel);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);

        RecyclerView recyclerView = binding.recyclerView;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                viewModel.loadMore();
            }
        });

        viewModel.scoreList.observe(this, scoreList -> {
            Boolean isLoading = viewModel.isLoading.getValue();
            boolean isRefreshing = viewModel.isRefreshing.get();
            controller.setData(scoreList, isLoading, isRefreshing);
        });

        viewModel.isLoading.observe(this, isLoading -> {
            List<Score> scoreList = viewModel.scoreList.getValue();
            boolean isRefreshing = viewModel.isRefreshing.get();
            controller.setData(scoreList, isLoading, isRefreshing);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
            // some actions here to open help activity
        }

        return super.onOptionsItemSelected(item);
    }
}