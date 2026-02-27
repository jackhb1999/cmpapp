package com.hb.dao.dept

import model.DeptParams

import com.hb.model.DeptEntity

interface DeptDao {

    suspend fun selectDeptList(dept: DeptParams): List<DeptEntity>
}