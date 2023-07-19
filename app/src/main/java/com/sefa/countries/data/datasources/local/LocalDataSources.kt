package com.sefa.countries.data.datasources.local

import com.sefa.countries.data.model.Countries
import javax.inject.Inject

class LocalDataSources
@Inject
constructor(private val countryDao: CountryDao)
{
    suspend fun getAllCountriesFromDao() = countryDao.getAllCountries()

    suspend fun insert(countriesList: List<Countries>)=countryDao.upsert(countriesList)

}