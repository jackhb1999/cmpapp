package com.hb.dao

import com.hb.model.DeptTable
import com.hb.dao.follows.FollowsTable
import com.hb.dao.post.PostTable
import com.hb.dao.post_likes.PostLikesTable
import com.hb.dao.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.exists
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


object DatabaseFactory {
    fun init() {
        Database.connect(createHikariDataSource())
        // print sql to std-out
//        addLogger(StdOutSqlLogger)
        transaction {
            if (!UserTable.exists()) {
                SchemaUtils.create(UserTable)
            }
            if (!DeptTable.exists()) {
                SchemaUtils.create(DeptTable)
            }
            if (!FollowsTable.exists()) {
                SchemaUtils.create(FollowsTable)
            }
            if (!PostTable.exists()) {
                SchemaUtils.create(PostTable)
            }
            if (!PostLikesTable.exists()) {
                SchemaUtils.create(PostLikesTable)
            }
        }
    }

    private fun createHikariDataSource(): HikariDataSource {
        val driverClass = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/socialmediadb"
        val username = "postgres"
        val password = "fackpg"

        val hikariConfig = HikariConfig().apply {
            driverClassName = driverClass
            setJdbcUrl(jdbcUrl)
            setUsername(username)
            setPassword(password)
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(hikariConfig)
    }


    suspend fun <T> dbQuery(block: suspend () -> T): T =
        suspendTransaction { withContext(Dispatchers.IO) { block() } }
//    newSuspendedTransaction(Dispatchers.IO) { block() }


}