package service

import kotlinx.rpc.annotations.Rpc
import model.SignParams
import model.User

@Rpc
interface UserService {

    // 用户注册
    suspend fun signUp(params: SignParams):Result<User>
    // 用户登录
    suspend fun signIn(params: SignParams):Result<User>


}