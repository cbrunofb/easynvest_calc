package com.easynvest.simulate.view

import android.content.Intent
import android.os.Bundle
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.easynvest.R
import kotlinx.android.synthetic.main.activity_form.*


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val intent = Intent(this, SimulateActivity::class.java)
        startActivity(intent)
    }
}
