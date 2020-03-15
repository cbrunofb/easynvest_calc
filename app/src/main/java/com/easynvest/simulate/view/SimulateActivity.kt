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
import com.easynvest.simulate.utils.enums.MaskType
import com.easynvest.simulate.utils.masks.TextViewMask
import com.easynvest.simulate.viewModel.SimulateViewModel
import kotlinx.android.synthetic.main.activity_simulate.*
import java.text.DecimalFormat
import kotlin.math.roundToInt


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
        setListeners()
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

    private fun setListeners() {
        btn_simulate_again.setOnClickListener {
            super.onBackPressed()
        }
    }

    //Observers
    private val simulateObserver = Observer<Simulate> {
        Log.v(TAG, "data updated $it")
        updateContent(it)
        applyMasks()
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
    //

    private fun updateContent(simulate: Simulate) {
        val df = DecimalFormat("0.00")

        tv_simulate_result_value.text = simulate.netAmount.toString()
        tv_total_profit_result.text = simulate.netAmountProfit.toString()
        tv_initial_value.text = simulate.investmentParameter?.investedAmount.toString()
        tv_brute_value.text = simulate.grossAmount.toString()
        tv_profits_value.text = simulate.grossAmountProfit.toString()
        tv_ir_over_profits_value.text = String.format("%s (%s%%)", simulate.taxesAmount.toString(), simulate.taxesRate.toString())
        tv_liquid_profits_value.text = simulate.netAmount.toString()
        tv_redemption_date_value.text = simulate.investmentParameter?.maturityDate.toString()
        tv_running_days_value.text = simulate.investmentParameter?.maturityTotalDays.toString()
        tv_monthly_profits_value.text = df.format(simulate.monthlyGrossRateProfit).toString()
        tv_cdi_percentage_value.text = simulate.investmentParameter?.rate?.roundToInt().toString()
        tv_year_profit_value.text = df.format(simulate.annualGrossRateProfit).toString()
        tv_period_profit_value.text = df.format(simulate.rateProfit).toString()
    }

    private fun applyMasks() {
        TextViewMask(tv_simulate_result_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_total_profit_result, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_initial_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_brute_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_profits_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_ir_over_profits_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_liquid_profits_value, MaskType.CURRENCY).applyMask()
        TextViewMask(tv_redemption_date_value, MaskType.DATE).applyMask()
        TextViewMask(tv_monthly_profits_value, MaskType.PERCENTAGE).applyMask()
        TextViewMask(tv_cdi_percentage_value, MaskType.PERCENTAGE).applyMask()
        TextViewMask(tv_year_profit_value, MaskType.PERCENTAGE).applyMask()
        TextViewMask(tv_period_profit_value, MaskType.PERCENTAGE).applyMask()
    }

    private fun showLoading() {
        layout_content.visibility = View.INVISIBLE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }
}

