package com.solt9029.editmasterandroid.view;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.ActivityScoreListBinding;
import com.solt9029.editmasterandroid.di.Injectable;
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;

import javax.inject.Inject;

public class ScoreListActivity extends AppCompatActivity implements Injectable {
    @Inject
    ViewModelProvider.Factory factory;
    private ScoreListViewModel viewModel;
    private ScoreListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        viewModel = ViewModelProviders.of(this, factory).get(ScoreListViewModel.class);

        ActivityScoreListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_score_list);
        controller = new ScoreListController(id -> viewModel.select(id));
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
                viewModel.onLoadMore();
            }
        });

        EditText editText = binding.editText;
        editText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.keyword.setValue(v.getText().toString());

                // close keyboard
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                return true;
            }
            return false;
        });

        viewModel.keyword.observe(this, keyword -> viewModel.onLoad());

        viewModel.resource.observe(this, resource -> {
            boolean isRefreshing = viewModel.isRefreshing.get();
            controller.setData(resource, isRefreshing);
        });

        viewModel.selectedId.observe(this, this::navigateToScoreActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
            navigateToHelpActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void navigateToHelpActivity() {
        Intent intent = HelpActivity.createIntent(ScoreListActivity.this);
        startActivity(intent);
    }

    public void navigateToScoreActivity(Integer id) {
        Intent intent = ScoreActivity.createIntent(ScoreListActivity.this, id);
        startActivity(intent);
    }
}