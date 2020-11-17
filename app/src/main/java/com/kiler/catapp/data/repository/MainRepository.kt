package com.kiler.catapp.data.repository

import com.kiler.catapp.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getBreeds() = apiHelper.getBreeds()
    suspend fun getImage() = apiHelper.getImage()

}