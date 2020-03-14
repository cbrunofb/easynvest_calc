package model

import java.util.*

data class InvestmentParameter(
    val investedAmount: Float,
    var yearlyInterestRate: Float,
    var maturityTotalDays: Int,
    var maturityBusinessDays: Int,
    var maturityDate: Date,
    var rate: Float,
    var isTaxFree: Float
)