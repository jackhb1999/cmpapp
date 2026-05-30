package com.hb.dao.post

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.dao.user.UserTable
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.plus
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import util.IdGenerator

private val logger = KotlinLogging.logger {}
class PostDaoImpl : PostDao {
    
    private fun postJoinTableSelect(): Query {
        return PostTable.join(
            otherTable = UserTable,
            onColumn = PostTable.userId,
            otherColumn = UserTable.id,
            joinType = JoinType.INNER
        ).selectAll()
    }

    override suspend fun createPost(
        caption: String,
        imageUrl: String,
        userId: String
    ): Boolean {
        return dbQuery {
            val insertStatement = PostTable.insert {
                it[postId] = IdGenerator.generateId()
                it[PostTable.content] = content
                it[PostTable.url] = url
                it[likesCount] = 0
                it[notLikesCount] = 0
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
                follows.forEach { logger.info { "Following: $it" } }
                postJoinTableSelect().where(PostTable.userId inList follows)
                    .orderBy(column = PostTable.postId, order = SortOrder.DESC)
                    .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                    .map { toPostRow(it) }
            } else {
                logger.info { "No follows found for $userId" }
                postJoinTableSelect()
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
            postJoinTableSelect()
                .where(PostTable.userId.eq(userId))
                .orderBy(column = PostTable.createdAt, order = SortOrder.DESC)
                .limit(pageSize).offset(((pageNumber - 1) * pageSize).toLong())
                .map { toPostRow(it) }
        }
    }

    override suspend fun getPost(postId: String): PostRow? {
        return dbQuery {
            postJoinTableSelect().where { PostTable.postId eq postId }
                .singleOrNull()?.let { toPostRow(it) }
        }
    }

    override suspend fun updateLikesCount(postId: String, decrement: Boolean): Boolean {
        return dbQuery {
            val value = if (decrement) -1 else 1
            PostTable.update({ PostTable.postId eq postId }) {
                it.update(column = likesCount, value = likesCount.plus(value))
            }
        } > 0
    }

    override suspend fun updateCommentsCount(postId: String, decrement: Boolean): Boolean {
        return dbQuery {
            val value = if (decrement) -1 else 1
            PostTable.update({ PostTable.postId eq postId }) {
                it.update(column = commentsCount, value = commentsCount.plus(value))
            }
        } > 0
    }

    override suspend fun deletePost(postId: String): Boolean {
        return dbQuery {
            PostTable.deleteWhere { PostTable.postId eq postId } > 0
        }
    }

    private fun toPostRow(row: ResultRow): PostRow {
        return PostRow(
            postId = row[PostTable.postId],
            content = row[PostTable.content],
            url = row[PostTable.url],
            likesCount = row[PostTable.likesCount],
            notLikesCount = row[PostTable.notLikesCount],
            commentsCount = row[PostTable.commentsCount],
            userId = row[PostTable.userId],
            createdAt = row[PostTable.createdAt],
        )
    }
}


