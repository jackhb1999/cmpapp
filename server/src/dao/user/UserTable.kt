package com.hb.dao.user

import org.jetbrains.exposed.v1.core.Table


object UserTable : Table(name = "user") {
    val id = varchar("user_id",36)
    val password = varchar("user_password", 100)
    val email = varchar("user_email", 50)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}



data class UserRow(
    val id: String,
    val password: String,
    val email: String,
)