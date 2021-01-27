package com.picpay.desafio.android.view

import com.picpay.desafio.android.base.BaseTest
import com.picpay.desafio.android.data.ResultState
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.util.buildListUsers
import com.picpay.desafio.android.util.buildListUsersEmpty
import com.picpay.desafio.android.util.observeForTesting
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest : BaseTest() {

    private val userRepository: UserRepository = mockk()

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(userRepository)
    }

    @Test
    fun `users - API retorna com sucesso`() {
        val resultSuccess = ResultState.Success(buildListUsers())

        coEvery { userRepository.getUsers() } returns resultSuccess.data

        viewModel.users.observeForTesting {
            assertEquals(viewModel.users.value, resultSuccess)
        }

    }

    @Test
    fun `users - API retorna com erro`() {
        val resultError = ResultState.Error

        viewModel.users.observeForTesting {
            assertEquals(viewModel.users.value, resultError)
        }

    }

    @Test
    fun `users - API retorna com lista vazia`() {
        val resultSuccessEmpty = ResultState.Success(buildListUsersEmpty())

        coEvery { userRepository.getUsers() } returns resultSuccessEmpty.data

        viewModel.users.observeForTesting {
            assertEquals(viewModel.users.value, ResultState.Error)
        }
    }
}