package com.hb.dao.user

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.security.hashPassword
import model.SignParams
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.plus
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import util.IdGenerator


class UserDaoImpl : UserDao {
    override suspend fun inert(params: SignParams): UserRow? {
        return dbQuery {
            val insertStatement = UserTable.insert {
                it[id] = IdGenerator.generateId()
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

    override suspend fun findById(userId: String): UserRow? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.id eq userId }.map { rowToUserRow(it) }.singleOrNull()
        }
    }



    private fun rowToUserRow(row: ResultRow): UserRow {
        return UserRow(
            id = row[UserTable.id],
            password = row[UserTable.password],
            email = row[UserTable.email],
        )
    }


}