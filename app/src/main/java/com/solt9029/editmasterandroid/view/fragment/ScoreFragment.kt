package com.solt9029.editmasterandroid.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.databinding.FragmentScoreBinding
import com.solt9029.editmasterandroid.view.activity.ScoreActivity
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class ScoreFragment : DaggerFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentScoreBinding
    private val viewModel: ScoreViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ScoreViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToScoreSettingsFragment.observe(this, "", Observer {
            (activity!! as ScoreActivity).navigateToScoreSettingsFragment()
        })
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        viewModel.thread = null
        super.onDestroy()
    }
}
