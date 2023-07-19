package com.sefa.countries.data.datasources.remote

import com.sefa.countries.data.model.CountriesResponse
import com.sefa.countries.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService
{
    @GET(Constants.END_POINT)
    suspend fun getCountryList() : Response<CountriesResponse>
}