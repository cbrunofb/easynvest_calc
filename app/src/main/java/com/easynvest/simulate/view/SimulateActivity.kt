package com.easynvest.simulate.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.easynvest.R
import com.easynvest.simulate.di.Injection
import com.easynvest.simulate.model.Simulate
import com.easynvest.simulate.model.SimulateParams
import com.easynvest.simulate.viewModel.SimulateViewModel
import kotlinx.android.synthetic.main.activity_simulate.*


class SimulateActivity : AppCompatActivity() {

    private lateinit var viewModel: SimulateViewModel

    companion object {
        const val TAG= "SIMULATE_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulate)

        val simulateParams = getParams()
        setupViewModel(simulateParams)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSimulate()
    }

    private fun getParams(): SimulateParams {
        return intent.getSerializableExtra(FormActivity.TAG) as SimulateParams
    }

    private fun setupViewModel(simulateParams: SimulateParams) {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(
            simulateParams
        )).get(
            SimulateViewModel::class.java)

        viewModel.simulate.observe(this, simulateObserver)
        viewModel.isViewLoading.observe(this, loadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    //Observers
    private val simulateObserver = Observer<Simulate> {
        Log.v(TAG, "data updated $it")
        layout_content.visibility = View.VISIBLE
    }

    private val loadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        if (it)
            showLoading()
        else
            hideLoading()
    }

    private val onMessageErrorObserver= Observer<String> {
        Log.v(TAG, "onMessageError $it")
        Toast.makeText(this, R.string.ops_error, Toast.LENGTH_SHORT).show()
        tv_error.visibility = View.VISIBLE
    }

    private fun showLoading() {
        layout_content.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }
}

