package com.easynvest.simulate.model

import java.util.*

data class InvestmentParameter(
    val investedAmount: Float?,
    var yearlyInterestRate: Float?,
    var maturityTotalDays: Int?,
    var maturityBusinessDays: Int?,
    var maturityDate: String?,
    var rate: Float?,
    var isTaxFree: String?
)