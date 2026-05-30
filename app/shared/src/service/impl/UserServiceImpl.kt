package service.impl

import common.KtorRpc
import kotlinx.rpc.withService
import model.SignParams
import model.User
import service.AwesomeService
import service.UserService

internal class UserServiceImpl : UserService, KtorRpc() {

    val service = rpcClient.withService<UserService>()

    override suspend fun signUp(params: SignParams): Result<User> = service.signUp(params)


    override suspend fun signIn(params: SignParams): Result<User> = service.signIn(params)
}