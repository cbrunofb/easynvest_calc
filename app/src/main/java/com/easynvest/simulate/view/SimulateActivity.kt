package com.easynvest.simulate.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easynvest.R
import com.easynvest.simulate.di.Injection
import com.easynvest.simulate.model.Simulate
import com.easynvest.simulate.model.SimulateParams
import com.easynvest.simulate.viewModel.SimulateViewModel

class SimulateActivity : AppCompatActivity() {

    private lateinit var viewModel: SimulateViewModel

    companion object {
        const val TAG= "SIMULATE_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSimulate()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(
            // TODO: Remove this mock
            SimulateParams(
                32323f,
                "CDI",
                3.4f,
                "no",
                "2021-03-14"
            )
        )).get(
            SimulateViewModel::class.java)

        viewModel.simulate.observe(this, simulateObserver)
        viewModel.isViewLoading.observe(this, loadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    //Observers
    private val simulateObserver = Observer<Simulate> {
        Log.v(TAG, "data updated $it")
        // TODO
    }

    private val loadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        // TODO
    }

    private val onMessageErrorObserver= Observer<String> {
        Log.v(TAG, "onMessageError $it")
        // TODO
    }
}

