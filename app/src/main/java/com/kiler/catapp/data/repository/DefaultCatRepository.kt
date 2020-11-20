package com.kiler.catapp.data.repository

import android.util.Log
import com.kiler.catapp.data.api.CatApi
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.utils.Resource
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCatRepository @Inject constructor(
    private val catApi: CatApi
) {
    suspend fun myFetchBreeds(): Resource<List<Breed>> {

        return try {
            val response = catApi.getBreeds()
            if (response.isSuccessful) {
                response.body()?.let {breeds->


                    var index = 0

                    CoroutineScope(Dispatchers.IO).launch {

                        (0..breeds.size-1).map {
                            async {

                                val imgUrl = catApi.getImage(breeds[it].id)

                                index++
                                breeds[it].image = imgUrl.body()!![0].url

                            }
                        }.awaitAll()



                    }

                    Log.e("defcatrepository", "${breeds[3].name} i obrazek ${breeds[3].image}")

                    return@let Resource.success(breeds)

                } ?: Resource.error(null,"Unknown errror.")
            } else {
                Resource.error(null,"Unknown errror.")
            }
        } catch (e: Exception){
            Resource.error(null,"Server unavailable." )
        }

    }


}