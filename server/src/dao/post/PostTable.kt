package com.hb.dao.post

import com.hb.dao.user.UserTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.datetime.CurrentDateTime

object PostTable : Table("posts") {
    val postId = varchar("post_id",21).uniqueIndex()
    val content = text(name ="content")
    val url = varchar(name ="url", length = 300)
    val likesCount = integer("likes_count")

    val notLikesCount = integer("not_likes_count")
    val commentsCount = integer("comments_count")
    val userId = varchar("user_id",21).references(ref= UserTable.id, onUpdate = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").defaultExpression(defaultValue = CurrentDateTime)
}

data class PostRow(
    val postId: String,
    val content: String,
    val url: String,
    val likesCount: Int,
    val notLikesCount: Int,
    val commentsCount: Int,
    val userId: String,
    val createdAt: LocalDateTime,
)