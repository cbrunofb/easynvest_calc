package com.easynvest.simulate.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.easynvest.simulate.di.Injection
import com.easynvest.R
import com.easynvest.simulate.viewModel.SimulateViewModel

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val intent = Intent(this, SimulateActivity::class.java)
        startActivity(intent)
    }
}
