package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import repository.UserRepository
import service.AwesomeServiceImpl
import usecase.AwesomeUseCase
import usecase.SignInUseCase
import usecase.SignUpUseCase
import util.Result
import kotlin.getValue

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val awesomeUseCase: AwesomeUseCase
) : ViewModel() {


    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn() {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            val authResultData = signInUseCase(
                email = uiState.email,
                password = uiState.password,
            )
            println(authResultData.toString())

            println(authResultData.message)
            println("37 :" + awesomeUseCase.daysUntilStableRelease())
            uiState = when (authResultData) {
                is Result.Error<*> -> uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = authResultData.message,
                )

                is Result.Success<*> -> uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true,
                )
            }
        }
    }


    fun updateEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

}

// 不可变对象
data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)
