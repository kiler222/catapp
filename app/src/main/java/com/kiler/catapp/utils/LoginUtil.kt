package com.kiler.catapp.utils

object LoginUtil {

    fun validateLoginInput(username: String, password: String): Boolean{
        if (username == "" || password == "") return false

        return true
    }
}