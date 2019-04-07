package com.solt9029.editmasterandroid.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.FragmentScoreListBinding;
import com.solt9029.editmasterandroid.view.EndlessScrollListener;
import com.solt9029.editmasterandroid.view.ScoreListController;
import com.solt9029.editmasterandroid.view.activity.ScoreListActivity;
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class ScoreListFragment extends DaggerFragment {
    @Inject
    ViewModelProvider.Factory factory;
    private FragmentScoreListBinding binding;
    private ScoreListActivity activity;
    private ScoreListViewModel viewModel;
    private ScoreListController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = Objects.requireNonNull((ScoreListActivity) getActivity());

        viewModel = ViewModelProviders.of(activity, factory).get(ScoreListViewModel.class);
        binding.setViewModel(viewModel);

        controller = new ScoreListController(id -> viewModel.select(id));
        binding.setAdapter(controller.getAdapter());

        Toolbar toolbar = binding.toolbar;
        activity.setSupportActionBar(toolbar);
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
                InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
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

        viewModel.selectedId.observe(this, activity::navigateToScoreActivity);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.help) {
            activity.navigateToHelpActivity();
        }
        return super.onOptionsItemSelected(item);
    }
}