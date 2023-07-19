package com.sefa.countries.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sefa.countries.data.datasources.local.CountryDao
import com.sefa.countries.data.datasources.local.CountryDatabase
import com.sefa.countries.data.datasources.local.LocalDataSources
import com.sefa.countries.data.datasources.remote.ApiService
import com.sefa.countries.data.model.Countries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Provides
    @Singleton
    fun provideContext(application: Application) : Context
    {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : CountryDatabase
    {
        return Room.databaseBuilder(
            context,
            CountryDatabase::class.java,"CountryDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryDao(database: CountryDatabase) : CountryDao =
        database.countryDao()

    @Provides
    @Singleton
    fun provideLocalDataSources(countryDao: CountryDao) : LocalDataSources
        = LocalDataSources(countryDao)

    @Provides
    fun provideCountries() = Countries()
}