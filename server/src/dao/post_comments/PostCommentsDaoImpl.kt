package com.hb.dao.post_comments

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.dao.user.UserTable
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertReturning
import org.jetbrains.exposed.v1.jdbc.selectAll
import util.IdGenerator

class PostCommentsDaoImpl : PostCommentsDao {
    override suspend fun addComment(
        postId: String,
        userId: String,
        content: String
    ): PostCommentRow? {
        return dbQuery {
            PostCommentsTable.insertReturning {
                it[PostCommentsTable.commentId] = IdGenerator.generateId()
                it[PostCommentsTable.postId] = postId
                it[PostCommentsTable.userId] = userId
                it[PostCommentsTable.content] = content
            }.singleOrNull()?.let { toPostCommentRow(it) }
        }
    }

    override suspend fun removeComment(commentId: String, postId: String): Boolean {
        return dbQuery {
            PostCommentsTable.deleteWhere {
                ((PostCommentsTable.commentId eq commentId)) and (PostCommentsTable.postId eq postId)
            } > 0
        }
    }

    override suspend fun findComment(
        commentId: String,
        postId: String
    ): PostCommentRow? {
        return dbQuery {
            PostCommentsTable.join(
                otherTable = UserTable,
                onColumn = PostCommentsTable.userId,
                otherColumn = UserTable.id,
                joinType = JoinType.INNER
            ).selectAll().where {
                (PostCommentsTable.commentId eq commentId) and (PostCommentsTable.postId eq postId)
            }.singleOrNull()?.let { toPostCommentRow(it) }
        }
    }

    override suspend fun getComments(
        postId: String,
        pageNumber: Int,
        pageSize: Int
    ): List<PostCommentRow>? {
        return dbQuery {
            PostCommentsTable.join(
                otherTable = UserTable,
                onColumn = PostCommentsTable.userId,
                otherColumn = UserTable.id,
                joinType = JoinType.INNER
            ).selectAll().where {
                (PostCommentsTable.postId eq postId)
            }.orderBy(column = PostCommentsTable.createAt, SortOrder.DESC)
                .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                .map { toPostCommentRow(it) }
        }
    }

    private fun toPostCommentRow(row: ResultRow): PostCommentRow {
        return PostCommentRow(
            commentId = row[PostCommentsTable.commentId],
            content = row[PostCommentsTable.content],
            postId = row[PostCommentsTable.postId],
            userId = row[PostCommentsTable.userId],
            username = row[UserTable.name],
            userImageUrl = row[UserTable.imageUrl],
            createdAt = row[PostCommentsTable.createAt]
        )
    }
}