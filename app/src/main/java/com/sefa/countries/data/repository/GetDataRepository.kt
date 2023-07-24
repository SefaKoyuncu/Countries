package com.sefa.countries.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sefa.countries.data.datasources.TimeDataSources
import com.sefa.countries.data.datasources.local.LocalDataSources
import com.sefa.countries.data.datasources.remote.RemoteDataSources
import com.sefa.countries.data.model.Countries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.sefa.countries.utils.Constants
import kotlinx.coroutines.coroutineScope


class GetDataRepository
@Inject
constructor( private val remoteDataSources: RemoteDataSources? // network
            ,private val localDataSources: LocalDataSources? // database
            ,private val timeDataSources: TimeDataSources //datastore
            ,private val context: Context)
{
    var returnList : List<Countries>? = null
    var currentTime : Long? = null
    var isTimeChanging : Boolean = false

    private val snackbar_ = MutableLiveData<Boolean>()
    val getSnackbar : LiveData<Boolean>
        get() = snackbar_

    fun isOnline(context: Context): Boolean
    {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    suspend fun getAllData() : Flow<List<Countries>?>
    {
        return flow {
            timeDataSources.getTime().let{
                it.collect{ longTime ->

                    if (!isTimeChanging) {
                        if (longTime == null) {
                            emit(getData(0L))
                        } else {
                            emit(getData(longTime))
                        }
                    }

                    /*if (!isTimeChanging)
                    {
                        getData(longTime!!)
                        //emit( getData(longTime!!))
                        isTimeChanging=true
                    }*/
                }
            }
        }
    }

    suspend fun getData(longTime : Long?) : List<Countries>?
    {
        if (isOnline(context))
        {
            if (longTime == null)
            {
                Log.e("if ici","longTime : null")
                Log.e("getDataFromNetwork()", "getDataFromNetwork()")
                return  getDataFromNetwork()
            }
            else
            {
                currentTime = System.currentTimeMillis()

                if (currentTime!! - longTime >= Constants.FORTY_SECONDS)
                {
                    Log.e("else-if ici","currentTime : $currentTime")
                    Log.e("else-if ici","time : $longTime")
                    Log.e("else-if ici","fark : ${currentTime!! -longTime}")
                    Log.e("getDataFromNetwork()", "getDataFromNetwork()")
                    return  getDataFromNetwork()
                }
                else
                {
                    Log.e("else-if ici","currentTime : $currentTime")
                    Log.e("else-if ici","time : $longTime")
                    Log.e("else-if ici","fark : ${currentTime!! -longTime}")
                    Log.e("getDataFromDatabase()", "getDataFromDatabase()")
                    return  getDataFromDatabase()
                }
            }
        }
        else //no internet
        {
            return getDataFromDatabase()
        }
    }


    suspend fun getDataFromNetwork() : List<Countries>?
    {
        remoteDataSources?.getCountriesFromApi()?.let { response ->
            if (response.isSuccessful)
            {
                val result=response.body()
                result?.let {list ->
                    returnList = list.countries
                    insertDataToRoom(list.countries)

                    val currentTime = System.currentTimeMillis()
                    insertTime(currentTime)
                }
            }
        }
        return returnList
    }

    suspend fun insertDataToRoom(dataListToRoom : List<Countries>) = coroutineScope {
        localDataSources?.insert(dataListToRoom)
    }

    suspend fun insertTime(longTime : Long) = coroutineScope {
        timeDataSources.saveTime(longTime)
    }

    suspend fun getDataFromDatabase() : List<Countries>?
    {
        localDataSources?.getAllCountriesFromDao()?.let {
            if (it.isNotEmpty())
            {
                returnList = it
            }
            else
            {
                Log.e("tag","getDataFromRoom Error")
            }
        }
        return returnList
    }
}