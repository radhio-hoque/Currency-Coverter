package com.radhio.exchangecurrency.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhio.exchangecurrency.models.Rates
import com.radhio.exchangecurrency.repositories.MainRepository
import com.radhio.exchangecurrency.util.DispatcherProvider
import com.radhio.exchangecurrency.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Math.round
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 *
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */

private const val API_KEY = "6020bd1fb2b30ab66409ff6bf90503c7"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    sealed class CurrencyEvent {
        class Success (val resultText: String): CurrencyEvent()
        class Failure (val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion : StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr:String,
        fromCurrency:String,
        toCurrency:String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount==null){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatcher.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRates(API_KEY,fromCurrency)){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency,rates)
                    if (rate == null){
                        _conversion.value = CurrencyEvent.Failure("Unexpected Error")
                    } else {
                        val convertedCurrency = (fromAmount * rate * 100).roundToInt() /100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }

}