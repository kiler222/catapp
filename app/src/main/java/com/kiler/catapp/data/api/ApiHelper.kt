package com.kiler.catapp.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getBreeds() = apiService.getBreeds()
    suspend fun getImage(breedID: String) = apiService.getImage(breedID)
}