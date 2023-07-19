package com.sefa.countries.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "countries")
@kotlinx.parcelize.Parcelize
data class Countries (
  @PrimaryKey //autoGenerate = true
  @SerializedName("id"       ) var id       : String? = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("image"    ) var image    : String? = null,
  @SerializedName("text"     ) var text     : String? = null,
  @SerializedName("capital"  ) var capital  : String? = null,
  @SerializedName("currency" ) var currency : String? = null

) : Parcelable