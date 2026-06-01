package service.impl

import common.KtorRpc
import kotlinx.rpc.withService
import model.SignParams
import model.User
import service.AwesomeService
import service.UserService
import util.ActionResult

internal class UserServiceImpl : UserService, KtorRpc() {

    val service = rpcClient.withService<UserService>()

    override suspend fun signUp(params: SignParams): ActionResult<User> = service.signUp(params)


    override suspend fun signIn(params: SignParams): ActionResult<User> = service.signIn(params)
}