package com.picpay.desafio.android.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.util.CoroutineTestRule
import com.picpay.desafio.android.data.ResultState
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.util.observeForTesting
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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
        val resultSuccessEmpty = ResultState.Success(emptyList())

        coEvery { userRepository.getUsers() } returns resultSuccessEmpty.data

        viewModel.users.observeForTesting {
            assertEquals(viewModel.users.value, ResultState.Error)
        }
    }

    private fun buildListUsers(): List<User> {
        val users = ArrayList<User>()
        for (i in 1..5) {
            users.add(
                User(
                    img = "url$i",
                    name = "name$i",
                    id = i,
                    username = "userName$i"
                )
            )
        }
        return users
    }
}