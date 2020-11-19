package com.kiler.catapp.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class LoginUtilTest{

    @Test
    fun `username empty return false`() {
        val result = LoginUtil.validateLoginInput(
            "",
            password = "asdfg"
        )
        assertThat(result).isFalse()

    }

    @Test
    fun `password empty return false`() {
        val result = LoginUtil.validateLoginInput(
            "John",
            password = ""
        )
        assertThat(result).isFalse()

    }

    @Test
    fun `username and password OK return true`() {
        val result = LoginUtil.validateLoginInput(
            "John",
            password = "asdfg"
        )
        assertThat(result).isTrue()

    }
}