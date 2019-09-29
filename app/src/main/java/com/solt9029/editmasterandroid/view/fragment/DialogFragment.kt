package com.solt9029.editmasterandroid.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.IdConstants
import com.solt9029.editmasterandroid.databinding.FragmentDialogBinding
import com.solt9029.editmasterandroid.view.ValidationErrorListController
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel
import dagger.android.DaggerDialogFragment
import javax.inject.Inject

class DialogFragment : DaggerDialogFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentDialogBinding
    private val viewModel: ScoreViewModel by lazy {
        ViewModelProviders.of(activity!! as FragmentActivity, factory).get(ScoreViewModel::class.java)
    }
    private val controller: ValidationErrorListController by lazy {
        ValidationErrorListController()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.fragment_dialog, null, false)
        builder.setView(binding.root)
        return builder.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.adapter = controller.adapter
        binding.setLifecycleOwner(activity!! as LifecycleOwner)

        viewModel.dialogType.observe(activity!! as LifecycleOwner, Observer {
            isCancelable = it != IdConstants.DialogType.LOADING
        })

        viewModel.validationErrorBody.observe(activity!! as LifecycleOwner, Observer {
            controller.setData(viewModel.validationErrorBody.value)
        })
    }
}
