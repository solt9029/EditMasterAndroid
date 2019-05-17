package com.solt9029.editmasterandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solt9029.editmasterandroid.R

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HelpActivity::class.java)
        }
    }
}
