package com.hb.model

import com.hb.annotation.Comment

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.exposedLogger
import org.jetbrains.exposed.v1.dao.EntityChangeType
import org.jetbrains.exposed.v1.dao.EntityHook
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.dao.toEntity
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

import org.koin.core.component.getScopeName
import kotlin.reflect.jvm.jvmName
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

abstract class BaseTable(name: String = "") : IntIdTable(name) {
    @Comment("创建者")
    val createBy = varchar("create_by", 1).default(defaultValue = "")

    @Comment("创建时间")
    val createTime = datetime("create_time").defaultExpression(CurrentDateTime)

    @Comment("更新者")
    val updateBy = varchar("update_by", 64).default(defaultValue = "")


    @Comment("更新时间")
    val updateTime = datetime("update_time")
}



abstract class BaseEntity(id: EntityID<Int>, table: BaseTable) : IntEntity(id) {
    var createBy by table.createBy
    var createTime by table.createTime
    var updateBy by table.updateBy
    var updateTime by table.updateTime
}



abstract class BaseEntityClass<out E : BaseEntity>(
    table: BaseTable
) : IntEntityClass<E>(table) {
    init {
        EntityHook.subscribe { change ->
            val changedEntity = change.toEntity(this)
            when (val type = change.changeType) {
                EntityChangeType.Updated -> {
                    val now = nowUTC()
                    changedEntity?.let {
                        if (it.writeValues[table.updateTime as Column<*>] == null) {
                            it.updateTime = now
                        }
                    }
                    logChange(changedEntity, type, now)
                }
                else -> logChange(changedEntity, type)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun nowUTC() = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    private fun logChange(entity: E?, type: EntityChangeType, dateTime: LocalDateTime? = null) {
        entity?.let {
            val entityClassName = this::class.jvmName
            exposedLogger.info(
                "$entityClassName(${it.id}) ${type.name.lowercase()} at ${dateTime ?: nowUTC()}"
            )
        }
    }
}