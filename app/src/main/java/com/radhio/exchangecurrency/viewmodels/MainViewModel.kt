package com.radhio.exchangecurrency.viewmodels

import androidx.lifecycle.ViewModel
import com.radhio.exchangecurrency.repositories.MainRepository
import com.radhio.exchangecurrency.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    sealed class CurrencyEvent {
        class Success (val resultText: String): CurrencyEvent()
        class Error (val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }
}