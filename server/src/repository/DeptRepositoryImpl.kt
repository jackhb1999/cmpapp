package com.hb.repository

import com.hb.dao.dept.DeptDao
import com.hb.dao.user.UserDao
import com.hb.model.DeptEntity
import model.DeptParams

class DeptRepositoryImpl(
    private val deptDao: DeptDao
) {
    suspend fun selectDeptList():List<DeptEntity>{
      var  dept: DeptParams = DeptParams(
          deptId = 1,
          deptName = "Dept",
          parentId = null,
          status = null
      )
       return deptDao.selectDeptList(dept);
    }

}