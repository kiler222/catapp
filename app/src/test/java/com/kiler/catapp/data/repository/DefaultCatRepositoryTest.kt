package com.kiler.catapp.data.repository

import com.kiler.catapp.data.api.CatApi
import com.kiler.catapp.data.model.Breed
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class DefaultCatRepositoryTest {


    lateinit var repository: DefaultCatRepository
    lateinit var catApi: CatApi


    @Before
    fun setup() {
        catApi = mock()
        repository = DefaultCatRepository(catApi)
    }

    @Test
    fun test_ApiReturnsEmptyList() {
        `when`(catApi.getBreeds()).thenReturn(Observable.just(emptyList<Breed>()))

        repository.getBreeds().test()
            .assertValue { it.isEmpty() }
    }


    @Test
    fun test_ApiReturnsNoEmptyBreedList() {
        `when`(catApi.getBreeds()).thenReturn(Observable.just(listOf(testBreed())))

        repository.getBreeds().test()
            .assertValueCount(1)
            .assertValue { it.size == 1 }
    }


    fun testBreed() = Breed("Test breed", "Worldwide", "Test name",
        "test", "", "WW", "calm", "")

}


