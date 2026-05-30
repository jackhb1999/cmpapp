package model

import kotlinx.serialization.Serializable
import util.Constants


@Serializable
data class SignParams(
    val email: String,
    val password: String,
)

@Serializable
data class User(
    val id: String = Constants.EMPTY_STR,
    val email: String = Constants.EMPTY_STR,
)