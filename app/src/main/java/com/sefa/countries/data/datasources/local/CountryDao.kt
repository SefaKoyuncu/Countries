package com.sefa.countries.data.datasources.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sefa.countries.data.model.Countries

interface CountryDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(countriesList: List<Countries>) //update and insert

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries() : List<Countries>
}