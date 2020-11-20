package com.kiler.catapp.data.model

import android.os.Build
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breed(
   // val imageUrl: String,

    val description: String,
    @SerializedName("origin")
    val country: String,
    val name: String,
    val id: String,
    val image: String,
    @SerializedName("country_code")
    val code: String,
    val temperament: String,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String

): Parcelable


