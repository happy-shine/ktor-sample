package com.shine.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.shine.util.KtorConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.nio.file.Path

val mapper = ObjectMapper(YAMLFactory())
val conf = mapper.readValue(Path.of("src/main/resources/application.yaml").toFile(), KtorConfig::class.java).jwt_user

fun Application.configureAuth() {
    install(Authentication) {

        jwt("user-jwt") {
            realm = "Access to 'login'"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(conf.secret))
                    .withAudience(conf.audience)
                    .withIssuer(conf.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }

        jwt("waiter-jwt") {

        }

        jwt("boss-jwt") {

        }
    }
}




