package com.kiler.catapp.data.model

import com.google.gson.annotations.SerializedName

data class Breed(
   // val imageUrl: String,

    val description: String,
    @SerializedName("origin")
    val country: String,
    val name: String,
    val id: String,
    var image: String

)


