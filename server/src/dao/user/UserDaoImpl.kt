package com.hb.dao.user

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.security.hashPassword
import model.SignUpParams
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.plus
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import util.IdGenerator


class UserDaoImpl : UserDao {
    override suspend fun inert(params: SignUpParams): UserRow? {
        return dbQuery {
            val insertStatement = UserTable.insert {
                it[id] = IdGenerator.generateId()
                it[name] = params.name
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUserRow(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): UserRow? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.email eq email }
                .map { rowToUserRow(it) }
                .singleOrNull()
        }
    }


    override suspend fun updateFollowsCount(
        follower: String,
        following: String,
        isFollowing: Boolean
    ): Boolean {
        return dbQuery {
            val count = if (isFollowing) +1 else -1
            val result1 = UserTable.update(
                where = { UserTable.id eq follower },
            ) {
                it.update(
                    column = UserTable.followingCount
                ) { UserTable.followingCount.plus(count) }
            } > 0
            val result2 = UserTable.update(
                where = { UserTable.id eq following },
            ) {
                it.update(UserTable.followersCount) { UserTable.followersCount.plus(count) }
            } > 0
            result1 && result2
        }
    }

    private fun rowToUserRow(row: ResultRow): UserRow {
        return UserRow(
            id = row[UserTable.id],
            name = row[UserTable.name],
            password = row[UserTable.password],
            email = row[UserTable.email],
            bio = row[UserTable.bio],
            imageUrl = row[UserTable.imageUrl],
            followersCount = row[UserTable.followersCount],
            followingCount = row[UserTable.followingCount],
        )
    }


}