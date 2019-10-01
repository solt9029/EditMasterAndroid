package com.solt9029.editmasterandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var dialog: DialogFragment = DialogFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.openCopyToast.observe(this, "openCopyToast", Observer {
            Toast.makeText(activity!!, "選択した行をコピーしました", Toast.LENGTH_SHORT).show()
        })

        viewModel.openDialog.observe(this, "openDialog", Observer {
            dialog.show(activity!!.fragmentManager, "dialog")
        })

        viewModel.navigateToScoreSettingsFragment.observe(this, "navigateToScoreSettingsFragment", Observer {
            (activity!! as ScoreActivity).navigateToScoreSettingsFragment()
        })
    }

    override fun onPause() {
        Timber.d("onPause")
        viewModel.thread = null
        viewModel.pauseVideo()
        super.onPause()
    }
}
