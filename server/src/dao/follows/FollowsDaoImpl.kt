package com.hb.dao.follows

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.model.FollowsTable
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll


class FollowsDaoImpl : FollowsDao {
    override suspend fun followUser(follower: ByteArray, following: ByteArray): Boolean {
        return dbQuery {
            val insertStatement = FollowsTable.insert {
                it[followerId] = follower
                it[followingId] = following
            }
            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun unfollowUser(follower: ByteArray, following: ByteArray): Boolean {
        return dbQuery {
            FollowsTable.deleteWhere {
                (FollowsTable.followerId eq follower) and (FollowsTable.followingId eq following)
            } > 0
        }
    }

    override suspend fun getFollowers(
        userId: ByteArray,
        pageNumber: Int,
        pageSize: Int
    ): List<ByteArray> {
        return dbQuery {
            FollowsTable.selectAll().where { FollowsTable.followerId eq userId }
                .orderBy(FollowsTable.followDate, SortOrder.DESC)
                .limit(pageSize)
                .offset(((pageNumber - 1) * pageSize).toLong())
                .map {
                    it[FollowsTable.followerId]
                }
        }
    }

    override suspend fun getFollowing(
        userId: ByteArray,
        pageNumber: Int,
        pageSize: Int
    ): List<ByteArray> {
        return dbQuery {
            FollowsTable.select(FollowsTable.followingId)
                .where { FollowsTable.followingId eq userId }
                .orderBy(FollowsTable.followDate, SortOrder.DESC)
                .limit(pageSize)
                .offset(((pageNumber - 1) * pageSize).toLong())
                .map { it[FollowsTable.followingId] }
        }
    }

    override suspend fun isAlreadyFollowing(follower: ByteArray, following: ByteArray): Boolean {
        return dbQuery {
           val queryResult = FollowsTable.selectAll().where {
                (FollowsTable.followerId eq follower) and (FollowsTable.followingId eq following)
            }
            queryResult.count() > 0
        }
    }
}