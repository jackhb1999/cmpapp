package com.hb.model

import com.hb.annotation.Comment
import org.jetbrains.exposed.v1.core.dao.id.EntityID

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.datetime

@Comment("部门表")
object DeptTable : IntIdTable(name = "sys_dept") {

    @Comment("父部门id")
    val pid = integer("parent_id").default(0)

    @Comment("祖级列表")
    val ancestors = varchar("ancestors", 50).default("")

    @Comment("部门名称")
    val deptName = varchar("dept_name", 30).default("")

    @Comment("显示顺序")
    val orderNum = integer("order_num").default(0)

    @Comment("负责人")
    val leader = varchar("leader", 20).nullable().default(defaultValue = null)

    @Comment("联系电话")
    val phone = varchar("phone", 11).nullable().default(defaultValue = null)

    @Comment("邮箱")
    val email = varchar("email", 50).nullable().default(defaultValue = null)

    @Comment("部门状态（0正常 1停用）")
    val status = char("status", 1).default(defaultValue = "0")

    @Comment("删除标志（0代表存在 2代表删除）")
    val delFlag = char("del_flag", 1).default(defaultValue = "0")

    @Comment("创建者")
    val createBy = varchar("create_by", 1).default(defaultValue = "")

    @Comment("创建时间")
    val createTime = datetime("create_time")

    @Comment("更新者")
    val updateBy = varchar("update_by", 64).default(defaultValue = "")

    @Comment("更新时间")
    val updateTime = datetime("update_time")

}

class DeptEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DeptEntity>(DeptTable)

    var pid by DeptTable.pid
    var ancestors by DeptTable.ancestors
    var deptName by DeptTable.deptName
    var orderNum by DeptTable.orderNum
    var leader by DeptTable.leader
    var phone by DeptTable.phone
    var email by DeptTable.email
    var status by DeptTable.status
    var delFlag by DeptTable.delFlag
    var createBy by DeptTable.createBy
    var createTime by DeptTable.createTime
    var updateBy by DeptTable.updateBy
    var updateTime by DeptTable.updateTime
}
