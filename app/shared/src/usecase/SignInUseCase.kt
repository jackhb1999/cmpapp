package usecase

import model.AuthResponse
import model.SignInParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.UserRepository

import kotlin.getValue

class SignInUseCase : KoinComponent {
    private val repository: UserRepository by inject()

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<AuthResponse> {
        if (email.isBlank() || password.isBlank()) {
            throw Throwable(message = "Invalid params")
        }
        val params = SignInParams(
            email = email,
            password = password,
        )
        return repository.signIn(params)
    }

}