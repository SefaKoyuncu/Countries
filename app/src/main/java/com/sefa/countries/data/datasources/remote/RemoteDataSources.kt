package com.sefa.countries.data.datasources.remote

import javax.inject.Inject

class RemoteDataSources
@Inject
constructor(private val apiService: ApiService)
{
    suspend fun getCountriesFromApi()=apiService.getCountryList()
}