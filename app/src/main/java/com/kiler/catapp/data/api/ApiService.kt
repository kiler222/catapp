package com.kiler.catapp.data.api


import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("x-key-api: 4f83fa01-6032-4f0b-bd37-6f39fe5ba345")
    @GET("breeds")
    suspend fun getBreeds(): Response<List<Breed>>

    @Headers("x-key-api: 4f83fa01-6032-4f0b-bd37-6f39fe5ba345")
    @GET("images/search")
    suspend fun getImage(@Query("breed_id") breedID: String): Response<List<BreedImage>>

//

}