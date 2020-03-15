package com.easynvest.simulate.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easynvest.simulate.model.Simulate
import com.easynvest.simulate.data.SimulateDataSource
import com.easynvest.simulate.data.OperationCallback

class SimulateViewModel(private val repository: SimulateDataSource): ViewModel() {

    private val _simulate = MutableLiveData<Simulate>()
    val simulate: LiveData<Simulate> = _simulate

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    fun loadSimulate(){
        _isViewLoading.postValue(true)
        repository.retrieveSimulate(object: OperationCallback<Simulate> {
            override fun onError(error: String?) {
                this@SimulateViewModel._isViewLoading.postValue(false)
                this@SimulateViewModel._onMessageError.postValue(error)
            }

            override fun onSuccess(simulate: Simulate?) {
                this@SimulateViewModel._isViewLoading.postValue(false)

                if(simulate != null){
                    this@SimulateViewModel._simulate.value = simulate
                }
            }
        })
    }
}