package com.radhio.exchangecurrency.util

/**
 * Created by Azmia Hoque Radhio on 1/6/2022.
 */
sealed class Resource<T>(val data : T?, val message:String?) {
    class Success<T> (data: T?): Resource<T>(data,null)
    class Error<T> (message: String?): Resource<T>(message = message, data = null)
}