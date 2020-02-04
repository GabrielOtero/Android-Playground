package com.example.notes

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {


    override fun setContentView(layoutResID: Int) {

        val constraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById(R.id.activity_content) as FrameLayout

        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
    }

    fun showProgressBar(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}