package com.picpay.desafio.android.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.picpay.desafio.android.data.ResultState
import com.picpay.desafio.android.data.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val users: LiveData<ResultState> = liveData {
        runCatching {
            repository.getUsers()
        }.onSuccess {
            if (it.isNotEmpty()) {
                emit(ResultState.Success(it))
            } else {
                emit(ResultState.Error)
            }
        }.onFailure {
            emit(ResultState.Error)
        }
    }

    class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}