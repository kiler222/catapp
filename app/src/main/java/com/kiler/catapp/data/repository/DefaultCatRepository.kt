package com.kiler.catapp.data.repository

import com.kiler.catapp.data.api.CatApi
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.model.BreedImage
import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.Observable

@Singleton
class DefaultCatRepository @Inject constructor(
    private val catApi: CatApi
) {

    fun getBreeds(): Observable<List<Breed>> {
            return catApi.getBreeds()
    }


    fun getImage(breedID: String): Observable<List<BreedImage>> {
        return catApi.getImage(breedID)
    }

}