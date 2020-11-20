package com.kiler.catapp.model

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.repository.DefaultCatRepository
import kotlinx.coroutines.launch


class CatViewModel @ViewModelInject constructor(private val repository: DefaultCatRepository) : ViewModel() {


    //a list of breeds as a livedata
    val mutableLiveBreeds: MutableLiveData<List<Breed>> by lazy {
        MutableLiveData<List<Breed>>()
    }


    fun fetchBreeds() {

        viewModelScope.launch {

            //fetch a complete list of breeds and present it in livedata
            val response = repository.myFetchBreeds()
            mutableLiveBreeds.postValue(response.data)

        }
    }

}