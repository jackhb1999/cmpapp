package com.hb.dao.post

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.dao.user.UserTable
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import util.IdGenerator

class PostDaoImpl : PostDao {

    val postJoinTableSelect = PostTable.join(
        otherTable = UserTable,
        onColumn = PostTable.userId,
        otherColumn = UserTable.id,
        joinType = JoinType.INNER
    ).selectAll()

    override suspend fun createPost(
        caption: String,
        imageUrl: String,
        userId: String
    ): Boolean {
        return dbQuery {
            val insertStatement = PostTable.insert {
                it[postId] = IdGenerator.generateId()
                it[PostTable.caption] = caption
                it[PostTable.imageUrl] = imageUrl
                it[likesCount] = 0
                it[commentsCount] = 0
                it[PostTable.userId] = userId
            }
            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun getFeedsPost(
        userId: String,
        follows: List<String>,
        pageNumber: Int,
        pageSize: Int
    ): List<PostRow> {
        return dbQuery {
            if (follows.isNotEmpty()) {
                postJoinTableSelect.where(PostTable.userId inList follows)
                    .orderBy(column = PostTable.createdAt, order = SortOrder.DESC)
                    .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                    .map { toPostRow(it) }
            } else {
                postJoinTableSelect
                    .orderBy(column = PostTable.likesCount, order = SortOrder.DESC)
                    .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                    .map { toPostRow(it) }
            }
        }
    }

    override suspend fun getPostByUser(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): List<PostRow> {
        return dbQuery {
            postJoinTableSelect
                .where(PostTable.userId.eq(userId))
                .orderBy(column = PostTable.createdAt, order = SortOrder.DESC)
                .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                .map { toPostRow(it) }
        }
    }

    override suspend fun getPost(postId: String): PostRow? {
        return dbQuery {
            postJoinTableSelect.where { PostTable.postId eq postId }
                .singleOrNull()?.let { toPostRow(it) }
        }
    }

    override suspend fun deletePost(postId: String): Boolean {
        return dbQuery {
            PostTable.deleteWhere { PostTable.postId eq postId } > 0
        }
    }

    private fun toPostRow(row: ResultRow): PostRow {
        return PostRow(
            postId = row[PostTable.postId],
            caption = row[PostTable.caption],
            imageUrl = row[PostTable.imageUrl],
            likesCount = row[PostTable.likesCount],
            commentsCount = row[PostTable.commentsCount],
            userId = row[PostTable.userId],
            username = row[UserTable.name],
            userImageUrl = row[UserTable.imageUrl]
        )
    }
}


