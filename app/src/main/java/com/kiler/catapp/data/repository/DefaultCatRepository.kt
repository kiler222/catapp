package com.kiler.catapp.data.repository

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

    //return a complete list of breeds with images
    suspend fun myFetchBreeds(): Resource<List<Breed>> {

        return try {
            val response = catApi.getBreeds()
            if (response.isSuccessful) {
                response.body()?.let {breeds->

                    var index = 0

                    //once all breeds are fetched, then for each of them I request the url of the breed image
                    CoroutineScope(Dispatchers.IO).launch {

                        (0..breeds.size-1).map {
                            async {

                                val imgUrl = catApi.getImage(breeds[it].id)

                                index++
                                breeds[it].image = imgUrl.body()!![0].url

                            }
                        }.awaitAll()



                    }


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