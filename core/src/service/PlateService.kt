package service

import kotlinx.rpc.annotations.Rpc
import model.PlateTree
import model.SignParams
import model.User
import util.ActionResult

@Rpc
interface PlateService {

    suspend fun getPlateTree(): ActionResult<List<PlateTree>>
}