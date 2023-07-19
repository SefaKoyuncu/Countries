package com.sefa.countries.data.model

import com.google.gson.annotations.SerializedName

data class CountriesResponse (

  @SerializedName("countries" ) var countries : List<Countries>

)