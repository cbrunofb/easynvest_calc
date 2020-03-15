package com.easynvest.simulate.data

import com.easynvest.simulate.model.InvestmentParameter
import com.easynvest.simulate.model.Simulate
import com.google.gson.annotations.SerializedName


class SimulateApiResponse {
    @SerializedName("investmentParameter")
    private val investmentParameter: InvestmentParameter? = null

    @SerializedName("grossAmount")
    private val grossAmount: Float? = null

    @SerializedName("taxesAmount")
    private val taxesAmount: Float? = null

    @SerializedName("netAmount")
    private val netAmount: Float? = null

    @SerializedName("grossAmountProfit")
    private val grossAmountProfit: Float? = null

    @SerializedName("netAmountProfit")
    private val netAmountProfit: Float? = null

    @SerializedName("annualGrossRateProfit")
    private val annualGrossRateProfit: Float? = null

    @SerializedName("monthlyGrossRateProfit")
    private val monthlyGrossRateProfit: Float? = null

    @SerializedName("dailyGrossRateProfit")
    private val dailyGrossRateProfit: Float? = null

    @SerializedName("taxesRate")
    private val taxesRate: Float? = null

    @SerializedName("rateProfit")
    private val rateProfit: Float? = null

    @SerializedName("annualNetRateProfit")
    private val annualNetRateProfit: Float? = null

    fun getSimulate(): Simulate {
        return Simulate(
            this.investmentParameter,
            this.grossAmount,
            this.taxesAmount,
            this.netAmount,
            this.grossAmountProfit,
            this.netAmountProfit,
            this.annualGrossRateProfit,
            this.monthlyGrossRateProfit,
            this.dailyGrossRateProfit,
            this.taxesRate,
            this.rateProfit,
            this.annualNetRateProfit
        )
    }
}