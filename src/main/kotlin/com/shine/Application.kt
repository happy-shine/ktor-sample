package com.shine

import com.shine.plugins.configureAuth
import com.shine.plugins.configureRouting
import com.shine.plugins.configureSerialization
import com.shine.util.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.191.22") {
        DatabaseFactory.init()
        configureSerialization()
        configureRouting()
        configureAuth()
    }.start(wait = true)
}


