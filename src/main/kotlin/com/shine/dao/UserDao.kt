package com.shine.dao

import com.shine.models.User

interface UserDao {

    suspend fun checkUserLogin(username: String, password: String): User?

    suspend fun userSignup(user: User): User?

}