package com.kiler.catapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kiler.catapp.data.model.Breed
import com.kiler.catapp.data.repository.DefaultCatRepository
import kotlinx.coroutines.launch
import javax.inject.Singleton


class CatViewModel @ViewModelInject constructor(private val repository: DefaultCatRepository) : ViewModel() {

    val mutableLiveBreeds: MutableLiveData<List<Breed>> by lazy {
        MutableLiveData<List<Breed>>()
    }



    fun fetchBreeds() {

        viewModelScope.launch {

            val response = repository.myFetchBreeds()
            mutableLiveBreeds.postValue(response.data)

        }
    }

}