package com.kiler.catapp.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import com.kiler.catapp.data.model.BreedImage
import com.kiler.catapp.data.model.BreedsList
import com.kiler.catapp.data.repository.DefaultCatRepository
import io.reactivex.Observable


class CatViewModel @ViewModelInject constructor(private val repository: DefaultCatRepository) : ViewModel() {

    private val TAG = "PJviewModel"

    fun getBreeds(): Observable<BreedsList> {
        return repository.getBreeds()
            .map {
                BreedsList(it, "Data OK")
            }
            .onErrorReturn {
                Log.e(TAG,"An error occurred $it")
                BreedsList(emptyList(), "An error occurred", it)
            }
    }

    fun getImage(breedID: String): Observable<BreedImage> {
        return repository.getImage(breedID)
            .map {
                BreedImage(it[0].url)
            }
            .onErrorReturn {
                Log.e(TAG,"An error occurred $it")
                BreedImage("")
            }
    }

}