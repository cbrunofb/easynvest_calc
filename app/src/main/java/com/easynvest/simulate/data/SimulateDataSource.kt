package com.easynvest.simulate.data

import com.easynvest.simulate.data.OperationCallback
import com.easynvest.simulate.model.Simulate

interface SimulateDataSource {
    fun retrieveSimulate(callback: OperationCallback<Simulate>)
    fun cancel()
}