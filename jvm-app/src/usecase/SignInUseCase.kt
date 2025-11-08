package usecase

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.UserRepository
import util.Result
import kotlin.getValue

class SignInUseCase : KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<AuthResponse> {
        if (email.isBlank() || password.isBlank()) {
            return Result.Error(message = "Invalid params")
        }
        val params = SignInParams(
            email = email,
            password = password,
        )
        return repository.signIn(params)
    }

}