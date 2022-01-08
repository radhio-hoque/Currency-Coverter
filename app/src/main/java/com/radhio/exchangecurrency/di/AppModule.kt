package com.radhio.exchangecurrency.di

import com.radhio.exchangecurrency.endpoints.CurrencyApi
import com.radhio.exchangecurrency.repositories.DefaultMainRepository
import com.radhio.exchangecurrency.repositories.MainRepository
import com.radhio.exchangecurrency.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.Unconfined
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */

private const val BASE_URL = "http://api.exchangeratesapi.io"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi() : CurrencyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi) : MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider {
       return object : DispatcherProvider{
            override val main: CoroutineDispatcher
                get() = Main
            override val io: CoroutineDispatcher
                get() = IO
            override val default: CoroutineDispatcher
                get() = Default
            override val unconfined: CoroutineDispatcher
                get() = Unconfined
        }
    }
}