package usecase

import com.hb.model.AuthResponse
import io.ktor.util.Digest
import model.LoginParam
import org.apache.commons.codec.digest.DigestUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.UserRepository
import util.Result

class LoginUseCase : KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(
        captcha:String,
        phoneNum:String,
        password:String,
    ): String? {
        if(captcha.isBlank() || phoneNum.isBlank() || password.isBlank()){
            return "Invalid params"
        }

        val params = LoginParam(
            phoneNum = phoneNum,
            password =  DigestUtils.md5Hex(password),
        )
        return repository.login(captcha,params)
    }

}