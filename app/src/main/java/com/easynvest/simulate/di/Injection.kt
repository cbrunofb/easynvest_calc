package com.easynvest.simulate.di

import androidx.lifecycle.ViewModelProvider
import com.easynvest.simulate.model.SimulateParams
import com.easynvest.simulate.data.SimulateRepository
import com.easynvest.simulate.viewModel.factory.ViewModelFactory

object Injection {

    fun provideViewModelFactory(simulateParams: SimulateParams): ViewModelProvider.Factory{
        val simulateDataSource = SimulateRepository(simulateParams)
        return ViewModelFactory(
            simulateDataSource
        )
    }
}