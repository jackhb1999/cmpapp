package com.hb.dao.plate

import com.hb.dao.DatabaseFactory.dbQuery
import io.github.oshai.kotlinlogging.KotlinLogging
import model.PlateTree
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.jdbc.selectAll

private val logger = KotlinLogging.logger {}

class PlateDaoImpl : PlateDao {
    override suspend fun getPlateList(): List<PlateRow> {
        return dbQuery {
            PlateTable.selectAll()
                .orderBy(PlateTable.id to SortOrder.ASC, PlateTable.pId to SortOrder.DESC)
                .map { toPlateRow(it) }
        }
    }

    private fun toPlateRow(row: ResultRow): PlateRow {
        return PlateRow(
            id = row[PlateTable.id],
            name = row[PlateTable.name],
            pId = row[PlateTable.pId],
            createdAt = row[PlateTable.createdAt],
        )
    }
}