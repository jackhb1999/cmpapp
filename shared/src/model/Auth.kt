package com.hb.model

import kotlinx.serialization.Serializable


@Serializable
data class SignUpParams(
    val name: String,
    val password: String,
    val email: String,
)

@Serializable
data class SignInParams(
    val email: String,
    val password: String,
)

@Serializable
data class AuthResponse(
    val data: UserSettingsData? = null,
    val errorMessage: String? = null
)

@Serializable
data class UserSettingsData(
    val id:Int = -1,
    val name: String = "",
    val bio:String = "",
    val avatar:String? = null,
    val token:String = "",
    val followersCount:Int = 0,
    val followingCount:Int = 0,
)