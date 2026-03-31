package com.hb.dao.post_comments

import com.hb.dao.post.PostTable
import com.hb.dao.post_likes.PostLikesTable
import com.hb.dao.user.UserTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime


object PostCommentsTable : Table(name = "post_comments") {
    val commentId = varchar("comment_id", 21).uniqueIndex()
    val postId = varchar("post_id", 21).references(PostTable.postId, onDelete = ReferenceOption.CASCADE)
    val userId = varchar("user_id", 21).references(UserTable.id, onDelete = ReferenceOption.CASCADE)
    val content = varchar("content", 200)
    val createAt = datetime("create_at").defaultExpression(CurrentDateTime)
}

data class PostCommentRow(
    val commentId: String,
    val content: String,
    val postId: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val createdAt: LocalDateTime
)