package com.shine.dao.impl

import com.shine.dao.UserDao
import com.shine.models.User
import com.shine.models.Users
import com.shine.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDaoImpl : UserDao {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        username = row[Users.username],
        password = row[Users.password],
        type = row[Users.type]
    )

    override suspend fun checkUserLogin(username: String, password: String): User? = dbQuery {
        Users
            .select { (Users.username eq username) and (Users.password eq password) }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun userSignup(user: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[type] = user.type
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }
}