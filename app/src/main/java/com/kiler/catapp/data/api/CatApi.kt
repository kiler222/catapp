package com.kiler.catapp.data.api


import com.kiler.catapp.BuildConfig
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApi {

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("breeds")
    suspend fun getBreeds(): Response<List<Breed>>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("images/search")
    suspend fun getImage(@Query("breed_id") breedID: String): Response<List<BreedImage>>



}