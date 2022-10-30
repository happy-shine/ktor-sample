package com.shine.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.shine.models.Customers
import com.shine.models.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Path


object DatabaseFactory {

    fun init() {
        val mapper = ObjectMapper(YAMLFactory())
        val conf =
            mapper.readValue(Path.of("src/main/resources/application.yaml").toFile(), KtorConfig::class.java).database
        val database = Database.connect(conf.url, conf.driver, user = conf.user, password = conf.password)

        transaction(database) {
            SchemaUtils.create(Customers)
            SchemaUtils.create(Users)
        }

    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
