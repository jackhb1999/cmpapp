package usecase

import com.hb.model.AuthResponse
import com.hb.model.SignUpParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.UserRepository
import util.Result

class SignUpUseCase: KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(
        email:String,
        name:String,
        password:String,
    ): Result<AuthResponse> {
        if(email.isBlank() || name.isBlank() || password.isBlank()){
            return Result.Error(message = "Invalid params")
        }
        val params = SignUpParams(
            email = email,
            name = name,
            password = password,
        )
        return repository.signUp(params)
    }

}