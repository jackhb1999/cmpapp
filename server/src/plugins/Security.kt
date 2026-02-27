package com.hb.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import model.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

private const val CLAIM = "email"
private lateinit var jwtAudience: String
private lateinit var jwtDomain: String
private lateinit var jwtRealm: String
private lateinit var jwtSecret: String


fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    jwtAudience = environment.config.property("jwt.audience").getString()
    jwtDomain = environment.config.property("jwt.domain").getString()
    jwtRealm = environment.config.property("jwt.realm").getString()
    jwtSecret = "secret"
    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(CLAIM).asString() != null) {

                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "Invalid or missing token"
                    )
                )
            }
        }
    }
}

fun generateJWTToken(email: String): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtDomain)
        .withClaim(CLAIM, email)
        .sign(Algorithm.HMAC256(jwtSecret))
}
