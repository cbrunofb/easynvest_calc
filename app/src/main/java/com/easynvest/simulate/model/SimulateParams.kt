package com.easynvest.simulate.model

import java.io.Serializable

data class SimulateParams(
    val investedAmount: Float?,
    var index: String = "CDI",
    var rate: Float?,
    var isTaxFree: String = "no",
    var maturityDate: String?
): Serializable