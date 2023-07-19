package com.sefa.countries.data.util

sealed class Resource<T>(val data : T? = null, val message : String? = null)
{
    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(message: String) : Resource<T>(message = message)

    data class Loading<T>(val isLoading : Boolean) : Resource<T>(null)
}
