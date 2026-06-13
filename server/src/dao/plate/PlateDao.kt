package com.hb.dao.plate

import model.PlateTree

interface PlateDao {

    suspend fun getPlateList(): List<PlateRow>

}