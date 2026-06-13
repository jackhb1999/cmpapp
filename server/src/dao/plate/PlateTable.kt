package com.hb.dao.plate

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

// 板块
object PlateTable : Table("plate") {
    val id = varchar("id",21).uniqueIndex()
    val pId = varchar("p_id",21)
    val name = varchar("name", 50)
    val createdAt = datetime("created_at").defaultExpression(defaultValue = CurrentDateTime)
}

data class PlateRow(
    val id: String,
    val pId: String,
    val name: String,
    val createdAt: LocalDateTime,
)