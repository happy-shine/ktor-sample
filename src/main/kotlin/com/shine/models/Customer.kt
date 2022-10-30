package com.shine.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Customer(
    val id: Int = -1,
    val name: String,
    val age: Int,
    val gender: Boolean
)

object Customers : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 32)
    val age = integer("age")
    val gender = bool("gender")

    override val primaryKey = PrimaryKey(id)
}