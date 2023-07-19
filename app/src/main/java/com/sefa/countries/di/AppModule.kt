package com.sefa.countries.di

import com.sefa.countries.data.datasources.remote.ApiService
import com.sefa.countries.data.datasources.remote.RemoteDataSources
import com.sefa.countries.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Provides
    fun provideBaseURL() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL : String) : ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSources
            = RemoteDataSources(apiService)
}