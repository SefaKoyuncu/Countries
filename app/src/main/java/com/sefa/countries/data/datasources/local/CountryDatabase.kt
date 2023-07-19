package com.sefa.countries.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sefa.countries.data.model.Countries

@Database(entities = [Countries::class], version = 1, exportSchema = false)
abstract class CountryDatabase  : RoomDatabase()
{
    abstract fun countryDao() : CountryDao
}