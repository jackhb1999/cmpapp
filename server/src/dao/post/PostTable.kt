package com.hb.dao.post

import com.hb.dao.plate.PlateTable
import com.hb.dao.user.UserTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.datetime.CurrentDateTime

object PostTable : Table("posts") {
    val postId = varchar("post_id", 21).uniqueIndex()

    // 帖子内容
    val content = text(name = "content")

    // 关联 url
    val url = varchar(name = "url", length = 255).nullable()

    val plateId = reference("plate_id", PlateTable.id, ReferenceOption.CASCADE)

    val likesCount = integer("likes_count")

    val notLikesCount = integer("not_likes_count")
    val commentsCount = integer("comments_count")

    val userId = varchar("user_id", 21).references(ref = UserTable.id, onUpdate = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").defaultExpression(defaultValue = CurrentDateTime)

    val updatedAt = datetime("updated_at").defaultExpression(defaultValue = CurrentDateTime)
}

data class PostRow(
    val postId: String,
    val content: String,
    val url: String?,
    val plateId: String,
    val plateName: String,
    val likesCount: Int,
    val notLikesCount: Int,
    val commentsCount: Int,
    val userId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)