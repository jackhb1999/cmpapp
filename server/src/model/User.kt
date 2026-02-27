package com.hb.model

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table


object UserRow : Table(name = "user") {
    val id = integer("user_id").autoIncrement()
    val name = varchar("user_name", 250)
    val password = varchar("user_password", 100)
    val email = varchar("user_email", 50)
    val bio = text(name = "user_bio").default(defaultValue = "Hey,what's up?")
    val avatar = text(name = "user_avatar").nullable()
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}



data class User(
    val id: Int,
    val name: String,
    val password: String,
    val email: String,
    val bio: String,
    val avatar: String?
){
  public  fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserRow.id],
            name = row[UserRow.name],
            password = row[UserRow.password],
            email = row[UserRow.email],
            bio = row[UserRow.bio],
            avatar = row[UserRow.avatar]
        )
    }
}