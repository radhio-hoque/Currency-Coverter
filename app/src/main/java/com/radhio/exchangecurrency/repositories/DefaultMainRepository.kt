package com.radhio.exchangecurrency.repositories

import com.radhio.exchangecurrency.endpoints.CurrencyApi
import com.radhio.exchangecurrency.models.CurrencyResponse
import com.radhio.exchangecurrency.util.Resource
import javax.inject.Inject
/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */

private const val API_KEY = "6020bd1fb2b30ab66409ff6bf90503c7"

class DefaultMainRepository @Inject constructor(
    private val  api : CurrencyApi): MainRepository
{
    override suspend fun getRates(apiKey:String,base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getCurrency(apiKey,base)
            val result = response.body()
            if (response.isSuccessful && result !=null){
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message?: "An error occurred")
        }
    }
}