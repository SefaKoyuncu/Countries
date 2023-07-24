package com.sefa.countries.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sefa.countries.data.model.Countries
import com.sefa.countries.data.repository.GetDataRepository
import com.sefa.countries.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GetDataViewModel
@Inject
constructor(private val repo : GetDataRepository) : ViewModel()
{
    private val response_ = MutableLiveData<Resource<List<Countries>>>()
    val getCountries : LiveData<Resource<List<Countries>>>
        get() = response_

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        try {

            repo.getAllData().collect{
                it?.let {res->
                    response_.postValue(Resource.Success(res))
                }
            }
        }
        catch (e : Exception)
        {
            response_.postValue(Resource.Error("Oops!, data didn't pull"))
            Log.e("error-getdataviewmodel : ",e.message.toString())
        }
    }
}