package com.easynvest.simulate.data

import com.easynvest.simulate.model.Simulate

interface OperationCallback<T> {
    fun onSuccess(simulate: Simulate?)
    fun onError(error: String?)
}