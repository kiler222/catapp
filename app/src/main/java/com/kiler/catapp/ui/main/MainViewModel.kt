package com.kiler.catapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiler.catapp.data.api.RetrofitBuilder
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.repository.MainRepository

import kotlinx.coroutines.*


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val TAG = "PJMainViewModel"

    val downloadStatus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

   val mutableLiveBreeds: MutableLiveData<List<Breed>> by lazy {
       MutableLiveData<List<Breed>>()
   }

    init {

        viewModelScope.launch(Dispatchers.IO) {
            Log.e(TAG, "w init launch")
            val fBreeds = fetchBreeds()
            updateBreedsImages(fBreeds)
        }

    }

    private suspend fun fetchBreeds(): List<Breed>{

            var result = emptyList<Breed>()
            try {
                val response = RetrofitBuilder.apiService.getBreeds()
                Log.e(TAG, "fetchBreeds, resp = ${response.isSuccessful}")
                if (response.isSuccessful && response.body() != null) {
                    result = response.body()!!

                   // mutableLiveBreeds.postValue(result)

                } else {
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(
//                            coroutineContext,
//                            "Error Occurred: ${response.message()}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
                    Log.e(TAG, "Error Occurred: ${response.message()}")

                }

            } catch (e: Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(this@MainActivity,
//                        "Error Occurred: ${e.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//                Log.e(TAG, e.message)

            }


        return result

    }



    private suspend fun updateBreedsImages(fBreeds: List<Breed>){

        val result = fBreeds
        var index = 0

        CoroutineScope(Dispatchers.IO).launch {

            (0..fBreeds.size-1).map {
                async {

                    val imgUrl = getImage(fBreeds[it].id)
                    index++
                    result[it].image = imgUrl
                    downloadStatus.postValue(((index.toFloat()/fBreeds.size.toFloat())*100).toInt())

                }
            }.awaitAll()

            mutableLiveBreeds.postValue(result)

        }

    }


    private suspend fun getImage(breedID: String): String {

        var imgUrl = ""

        try {
            val response = RetrofitBuilder.apiService.getImage(breedID)

            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!
                imgUrl = result[0].url

            } else {
                // Show API error.
//                withContext(Dispatchers.Main){
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Error Occurred: ${response.message()}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }

            }

        } catch (e: java.lang.Exception){
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@MainActivity,
//                    "Error Occurred: ${e.message}",
//                    Toast.LENGTH_LONG
//                ).show()
//            }

        }

        return imgUrl

    }


}