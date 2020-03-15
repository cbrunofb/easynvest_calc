package com.easynvest.simulate.model

data class SimulateParams(
    val investedAmount: Float?,
    var index: String?,
    var rate: Float?,
    var isTaxFree: String?,
    var maturityDate: String?
)