package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import usecase.LoginUseCase
import util.Result
import kotlin.random.Random

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn() {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            val authResultData = loginUseCase(
                captcha = uiState.captcha,
                phoneNum = uiState.phoneNum,
                password = uiState.password,
            )
            println(authResultData)

//            println(authResultData.message)
//            uiState = when (authResultData) {
//                is Result.Error<*> -> uiState.copy(
//                    isAuthenticating = false,
//                    authErrorMessage = authResultData.message,
//                )
//
//                is Result.Success<*> -> uiState.copy(
//                    isAuthenticating = false,
//                    authenticationSucceed = true,
//                )
//            }
        }
    }


    fun updatePhoneNum(phoneNum: String) {
        uiState = uiState.copy(phoneNum = phoneNum)
    }

    fun updateRandomCode(){
        uiState = uiState.copy(randomCode = Random(System.currentTimeMillis()).nextInt())
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun updateCaptcha(captcha: String) {
        uiState = uiState.copy(captcha = captcha)
    }

}

// 不可变对象
data class LoginUiState(
    var phoneNum: String = "",
    var password: String = "",
    var captcha: String = "",
    var randomCode: Int = Random(System.currentTimeMillis()).nextInt(),
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)
