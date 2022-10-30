package com.shine.plugins

import com.shine.routes.customerRouting
import com.shine.routes.loginRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        loginRouting()
        customerRouting()
    }
}
