package com.hb.dao.user

import org.jetbrains.exposed.v1.core.Table


object UserTable : Table(name = "user") {
    val id = varchar("user_id",21)
    val name = varchar("user_name", 250)
    val password = varchar("user_password", 100)
    val email = varchar("user_email", 50)
    val bio = text(name = "user_bio").default(defaultValue = "Hey,what's up?")
    val imageUrl = varchar(name = "image_url",300).nullable()
    val followersCount = integer("followers_count").default(defaultValue = 0)
    var followingCount = integer("following_count").default(defaultValue = 0)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}



data class UserRow(
    val id: String,
    val name: String,
    val password: String,
    val email: String,
    val bio: String,
    val imageUrl: String?,
    val followersCount: Int,
    val followingCount: Int,
)