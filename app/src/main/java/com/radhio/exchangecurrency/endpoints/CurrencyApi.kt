package com.radhio.exchangecurrency.endpoints

import com.radhio.exchangecurrency.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */
interface CurrencyApi {
    @GET("/v1/latest")
    suspend fun getCurrency(
        @Query("access_key") accessKey : String,
        @Query("base") base: String
    ) : Response<CurrencyResponse>
}