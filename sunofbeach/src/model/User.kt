package model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParam(val phoneNum: String, val password: String)





