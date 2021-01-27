package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.User

sealed class ResultState {
    data class Success(val data: List<User>) : ResultState()
    object Error : ResultState()
}