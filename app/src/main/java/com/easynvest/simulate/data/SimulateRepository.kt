package com.easynvest.simulate.data

import android.util.Log
import data.ApiClient
import com.easynvest.simulate.model.Simulate
import com.easynvest.simulate.model.SimulateParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimulateRepository(simulateParams: SimulateParams):
    SimulateDataSource {

    private var call: Call<SimulateApiResponse>? = null

    private var investedAmount = simulateParams.investedAmount
    private var index = simulateParams.index
    private var rate = simulateParams.rate
    private var isTaxFree = simulateParams.isTaxFree
    private var maturityDate = simulateParams.maturityDate

    companion object {
        const val TAG="SIMULATE_REPOSITORY"
    }

    override fun retrieveSimulate(callback: OperationCallback<Simulate>) {
        call = ApiClient.build()?.simulate(
            this.investedAmount!!,
            this.index,
            this.rate!!,
            this.isTaxFree,
            this.maturityDate!!
        )
        call?.enqueue(object : Callback<SimulateApiResponse> {
            override fun onFailure(call: Call<SimulateApiResponse>, t: Throwable) {
                callback.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<SimulateApiResponse>, response: Response<SimulateApiResponse>) {
                response.body()?.let {
                    if(response.isSuccessful){
                        Log.v(TAG, "simulate ${it.getSimulate()}")
                        callback.onSuccess(it.getSimulate())
                    } else {
                        callback.onError(response.message())
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}