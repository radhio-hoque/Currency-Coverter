package com.radhio.exchangecurrency.repositories

import com.radhio.exchangecurrency.models.CurrencyResponse
import com.radhio.exchangecurrency.util.Resource

/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */
interface MainRepository {
    suspend fun getRates(apiKey:String,base:String) : Resource<CurrencyResponse>
}