package com.kiler.catapp.data.model

data class BreedsList(val breeds: List<Breed>,
                      val message: String,
                      val error: Throwable? = null)