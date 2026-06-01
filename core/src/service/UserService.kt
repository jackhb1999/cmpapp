package service

import kotlinx.rpc.annotations.Rpc
import model.SignParams
import model.User
import util.ActionResult

@Rpc
interface UserService {

    // 用户注册
    suspend fun signUp(params: SignParams): ActionResult<User>
    // 用户登录
    suspend fun signIn(params: SignParams):ActionResult<User>


}