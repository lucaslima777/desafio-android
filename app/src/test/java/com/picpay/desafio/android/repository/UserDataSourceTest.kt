package com.picpay.desafio.android.repository

import com.picpay.desafio.android.base.BaseTest
import com.picpay.desafio.android.data.ResultState
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserDataSource
import com.picpay.desafio.android.util.buildListUsers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class UserDataSourceTest : BaseTest() {

    private val apiService: PicPayService = mockk()
    private lateinit var dataSource: UserDataSource

    @Before
    fun setup() {
        dataSource = UserDataSource(apiService)
    }

    @Test
    fun `getUsers - API retorna com sucesso`() = runBlockingTest {
        val resultSuccess = ResultState.Success(buildListUsers())

        coEvery { apiService.getUsers() } returns resultSuccess.data

        assertEquals(resultSuccess.data, apiService.getUsers())
    }
}