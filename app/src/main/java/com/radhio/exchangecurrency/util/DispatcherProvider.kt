package com.radhio.exchangecurrency.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */
interface DispatcherProvider {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
}