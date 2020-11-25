package com.kiler.catapp.data.api


import com.kiler.catapp.BuildConfig
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedImage
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApi {

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("breeds")
    fun getBreeds(): Observable<List<Breed>>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("images/search")
    fun getImage(@Query("breed_id") breedID: String): Observable<List<BreedImage>>

}