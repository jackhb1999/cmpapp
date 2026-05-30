package com.hb.dao.post_likes

import com.hb.dao.post.PostTable
import com.hb.dao.user.UserTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object PostLikesTable : Table(name = "post_likes") {
    val likeId = varchar("like_id", 36).uniqueIndex()
    val isLike = bool("is_like")
    val postId = varchar("post_id", 36).references(ref = PostTable.postId, onDelete = ReferenceOption.CASCADE)
    val userId = varchar("user_id", 36).references(ref = UserTable.id, onDelete = ReferenceOption.CASCADE)
    val likeDate = datetime("like_date").defaultExpression(defaultValue = CurrentDateTime)
}