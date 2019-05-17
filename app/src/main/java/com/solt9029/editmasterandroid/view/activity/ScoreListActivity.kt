package com.solt9029.editmasterandroid.view.activity

import android.os.Bundle
import com.solt9029.editmasterandroid.R
import dagger.android.support.DaggerAppCompatActivity

class ScoreListActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_list)
    }

    fun navigateToHelpActivity() {
        val intent = HelpActivity.createIntent(this)
        startActivity(intent)
    }

    fun navigateToScoreActivity(id: Int?) {
        val intent = ScoreActivity.createIntent(this, id)
        startActivity(intent)
    }
}