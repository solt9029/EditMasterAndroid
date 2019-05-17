package com.solt9029.editmasterandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.view.fragment.ScoreFragment
import com.solt9029.editmasterandroid.view.fragment.ScoreSettingsFragment
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel
import dagger.android.support.DaggerAppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import javax.inject.Inject

class ScoreActivity : DaggerAppCompatActivity() {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ScoreViewModel by lazy {
        ViewModelProviders.of(this, factory).get(ScoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val id = intent.getIntExtra(ID, 0)
        viewModel.initScore(id)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, ScoreFragment(), "ScoreFragment")
                .commit()
    }

    fun navigateToScoreSettingsFragment() {
        supportFragmentManager
                .beginTransaction()
                .addToBackStack("score")
                .replace(R.id.fragment_container, ScoreSettingsFragment(), "ScoreSettingsFragment")
                .commit()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    companion object {
        const val ID = "score_id" // for intent.

        fun createIntent(context: Context, id: Int?): Intent {
            return Intent(context, ScoreActivity::class.java).putExtra(ID, id)
        }
    }
}
