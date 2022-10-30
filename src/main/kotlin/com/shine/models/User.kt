package com.shine.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val type: Int
)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 32)
    val password = varchar("password", 32)
    val type = integer("type")

    override val primaryKey = PrimaryKey(id)
}

enum class Job {
    USER, WAITER, BOSS
}