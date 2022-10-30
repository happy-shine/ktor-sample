package com.shine.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.shine.dao.impl.UserDaoImpl
import com.shine.models.User
import com.shine.plugins.conf
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.loginRouting() {
    val dao = UserDaoImpl()

    post("login") {
        val formParameters = call.receiveParameters()
        val username = formParameters["username"].toString()
        val password = formParameters["password"].toString()
        val user = dao.checkUserLogin(username, password)

        if (user != null) {
            val token = JWT.create()
                .withAudience(conf.audience)
                .withIssuer(conf.issuer)
                .withClaim("username", username)
                .withExpiresAt(Date(System.currentTimeMillis() + conf.validityInMs))
                .sign(Algorithm.HMAC256(conf.secret))
            call.respond(mapOf("token" to token))
        } else {
            call.respond(mapOf("token" to "ERROR"))
        }
    }

    post("register") {
        val formParameters = call.receiveParameters()
        val username = formParameters["username"].toString()
        val password = formParameters["password"].toString()
        val type = formParameters["type"].toString().toInt()

        if (type in 0..2) {
            val user = User(0, username, password, type)
            val ans = dao.userSignup(user)
            call.respond(mapOf("user" to ans))
        } else {
            call.respondText("Type must in 0..2")
        }
    }

}
