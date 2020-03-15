package com.easynvest.simulate.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easynvest.simulate.data.SimulateDataSource
import com.easynvest.simulate.viewModel.SimulateViewModel


class ViewModelFactory(private val repository: SimulateDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SimulateViewModel(repository) as T
    }
}