package com.hb.util
//
//import com.hb.dao.follows.FollowsTable.registerColumn
//import diglol.id.Id
//import org.jetbrains.exposed.v1.core.BinaryColumnType
//import org.jetbrains.exposed.v1.core.Column
//import org.jetbrains.exposed.v1.core.LongColumnType
//import org.jetbrains.exposed.v1.core.dao.id.EntityID
//import org.jetbrains.exposed.v1.core.dao.id.IdTable
//import org.jetbrains.exposed.v1.core.java.javaUUID
//import util.IdGenerator
//import java.util.UUID
//
//
//open class IDTable(name: String = "", columnName: String = "id") : IdTable<Id>(name) {
//    final override val id: Column<EntityID<Id>> = binary()
//    final override val primaryKey = PrimaryKey(id)
//}
//
//
//fun long(name: String, checkConstraintName: String? = null): Column<Long> = registerColumn(name, BinaryColumnType()).apply {
//    check(checkConstraintName ?: "${generatedSignedCheckPrefix}long_${this.unquotedName()}") { it.between(Long.MIN_VALUE, Long.MAX_VALUE) }
//}