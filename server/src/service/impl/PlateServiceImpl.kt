package com.hb.service.impl

import com.hb.dao.plate.PlateDao
import model.PlateTree
import service.PlateService
import util.ActionResult
import util.send

class PlateServiceImpl(
    private val plateDao: PlateDao
) : PlateService {
    override suspend fun getPlateTree(): ActionResult<List<PlateTree>> {
        val plateList = plateDao.getPlateList()
        val result = ArrayList<PlateTree>()
        plateList.forEach { plateRow ->
            val treeItem = PlateTree(
                id = plateRow.id,
                name = plateRow.name,
                child = ArrayList()
            )
            val find = result.find { plateRow.pId == it.id }
            when (find) {
                null -> result.add(treeItem)
                else -> find.child.plus(treeItem)
            }
        }
        return Result.success(result).send()
    }
}