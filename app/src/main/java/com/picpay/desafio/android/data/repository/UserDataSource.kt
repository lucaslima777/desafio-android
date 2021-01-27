package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.model.User

class UserDataSource(private val service: PicPayService): UserRepository {
    override suspend fun getUsers(): List<User> = service.getUsers()
}