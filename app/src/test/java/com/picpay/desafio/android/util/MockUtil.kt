package com.picpay.desafio.android.util

import com.picpay.desafio.android.model.User

fun buildListUsers(): List<User> {
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

fun buildListUsersEmpty() = emptyList<User>()