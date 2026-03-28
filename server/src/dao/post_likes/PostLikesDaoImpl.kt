package com.hb.dao.post_likes

import com.hb.dao.DatabaseFactory.dbQuery
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import util.IdGenerator

class PostLikesDaoImpl : PostLikesDao {
    override suspend fun addLike(postId: String, userId: String): Boolean {
        return dbQuery {
            val insertStatement = PostLikesTable.insert {
                it[likeId] = IdGenerator.generateId()
                it[PostLikesTable.postId] = postId
                it[PostLikesTable.userId] = userId
            }
            insertStatement.resultedValues?.isNotEmpty() ?: false
        }
    }

    override suspend fun removeLike(postId: String, userId: String): Boolean {
        return dbQuery {
            PostLikesTable.deleteWhere {
                (PostLikesTable.postId eq postId) and (PostLikesTable.userId eq userId)
            } > 0
        }
    }

    override suspend fun isPostLiked(postId: String, userId: String): Boolean {
        return dbQuery {
            PostLikesTable.selectAll().where {
                (PostLikesTable.postId eq postId) and (PostLikesTable.userId eq userId)
            }.toList().isNotEmpty()
        }
    }
}