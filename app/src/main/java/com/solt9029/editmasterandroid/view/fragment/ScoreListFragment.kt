package com.solt9029.editmasterandroid.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.databinding.FragmentScoreListBinding
import com.solt9029.editmasterandroid.model.Score
import com.solt9029.editmasterandroid.view.EndlessScrollListener
import com.solt9029.editmasterandroid.view.ScoreListController
import com.solt9029.editmasterandroid.view.activity.ScoreListActivity
import com.solt9029.editmasterandroid.viewmodel.Resource
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ScoreListFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentScoreListBinding
    private val viewModel: ScoreListViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ScoreListViewModel::class.java)
    }
    private val controller: ScoreListController by lazy {
        ScoreListController { id -> viewModel.navigateToScoreActivity(id) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.adapter = controller.adapter

        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.toolbar_menu)

        val recyclerView = binding.recyclerView
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : EndlessScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                viewModel.onLoadMore()
            }
        })

        val editText = binding.editText
        editText.setOnEditorActionListener { v: TextView, actionId: Int, _: KeyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.keyword.value = v.text.toString()

                    // close keyboard
                    val manager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromWindow(editText.windowToken, 0)

                    true
                }
                else -> false
            }
        }

        viewModel.keyword.observe(this, Observer { _ -> viewModel.onLoad() })

        viewModel.resource.observe(this, Observer<Resource<List<Score>>> { resource ->
            val isRefreshing = viewModel.isRefreshing.value
            controller.setData(resource, isRefreshing)
        })

        viewModel.navigateToScoreActivity.observe(this, "",
                Observer<Int> { (activity!! as ScoreListActivity).navigateToScoreActivity(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.help) {
            (activity!! as ScoreListActivity).navigateToHelpActivity()
        }
        return super.onOptionsItemSelected(item)
    }
}
