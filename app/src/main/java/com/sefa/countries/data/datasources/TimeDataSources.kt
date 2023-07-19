package com.sefa.countries.data.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimeDataSources
@Inject
constructor(private val dataStore: DataStore<Preferences>)
{
    companion object{
        val timeKey = longPreferencesKey("time")
    }

    suspend fun saveTime(longTime : Long)
    {
        dataStore.edit {
            it[timeKey] = longTime
        }
    }

    suspend fun getTime() : Flow<Long?> = dataStore.data.map {
        it[timeKey]
    }
}