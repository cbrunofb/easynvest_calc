package com.easynvest.simulate.model

import java.io.Serializable
import java.util.*

data class Simulate(
    val investmentParameter: InvestmentParameter?,
    val grossAmount: Float?,
    val taxesAmount: Float?,
    val netAmount: Float?,
    val grossAmountProfit: Float?,
    val netAmountProfit: Float?,
    val annualGrossRateProfit: Float?,
    val monthlyGrossRateProfit: Float?,
    val dailyGrossRateProfit: Float?,
    val taxesRate: Float?,
    val rateProfit: Float?,
    val annualNetRateProfit: Float?
): Serializable