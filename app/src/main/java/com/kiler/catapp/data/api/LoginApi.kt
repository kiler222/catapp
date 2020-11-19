package com.kiler.catapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LoginApi {

    @Headers("x-key-api: 4f83fa01-6032-4f0b-bd37-6f39fe5ba345")
    @GET("login")
    suspend fun login(@Query("username") username: String,
                      @Query("password") password: String
    ): Response<Boolean>

}