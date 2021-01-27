package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}