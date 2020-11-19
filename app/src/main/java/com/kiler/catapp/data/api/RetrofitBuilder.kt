package com.kiler.catapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit singleton

object RetrofitBuilder {

    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val catApi: CatApi = getRetrofit().create(CatApi::class.java)

    val loginApi: LoginApi = getRetrofit().create(LoginApi::class.java)

}



