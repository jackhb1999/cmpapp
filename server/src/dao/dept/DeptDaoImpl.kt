package com.hb.dao.dept

import com.hb.dao.DatabaseFactory.dbQuery
import com.hb.model.DeptEntity
import com.hb.model.DeptTable
import model.DeptParams
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.selectAll


class DeptDaoImpl : DeptDao {
    override suspend fun selectDeptList(dept: DeptParams): List<DeptEntity> {

        return dbQuery {
            val conditions: Op<Boolean> = (DeptTable.delFlag eq "0")
            if (dept.deptId != null) {
                conditions and (DeptTable.id eq dept.deptId)
            }

            dept.parentId?.let { parentId ->
                {
                    conditions and (DeptTable.pid eq parentId)
                }
            }

            if (dept.deptName != null) {
                conditions and (DeptTable.deptName like dept.deptName!!)
            }


            dept.status?.apply {
                conditions and (DeptTable.status like this)
            }

            val orderBy = DeptEntity.find { conditions }
                .orderBy(
                    DeptTable.pid to SortOrder.ASC,
                    DeptTable.orderNum to SortOrder.DESC
                ).toList()
//            val a = DeptTable.selectAll()
//                .where(
//                    DeptTable.deptName eq dept.deptName!! and
//                            (DeptTable.status eq dept.status!!)
//                )
//                .orderBy(DeptTable.deptName to SortOrder.ASC)
            return@dbQuery orderBy
        }
    }
}