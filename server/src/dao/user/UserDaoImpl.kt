package com.hb.dao.user

import com.hb.dao.DatabaseFactory.dbQuery
import model.SignUpParams
import com.hb.model.User
import com.hb.model.UserRow
import com.hb.security.hashPassword
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll


class UserDaoImpl : UserDao {
    override suspend fun inert(params: SignUpParams): User? {
        return dbQuery {
            val insertStatement = UserRow.insert {
                it[name] = params.name
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): User? {
        return dbQuery {
            UserRow.selectAll().where { UserRow.email eq email }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserRow.id],
            name = row[UserRow.name],
            password = row[UserRow.password],
            email = row[UserRow.email],
            bio = row[UserRow.bio],
            avatar = row[UserRow.avatar]
        )
    }
}