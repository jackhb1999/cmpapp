package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokar.sonner.ToasterState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext.get
import util.Result

class SignUpViewModel(

) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set



    fun signUp() {
        viewModelScope.launch {
//            uiState = uiState.copy(isAuthenticating = true)
//            val authResultData = signUpUseCase(
//                email = uiState.email,
//                name = uiState.username,
//                password = uiState.password,
//            )
//            uiState = when (authResultData) {
//                is Result.Error -> uiState.copy(
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


    fun updateUsername(username: String) {
        uiState = uiState.copy(username = username)
    }

    fun updateEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

}

// 不可变对象
data class SignUpUiState(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)
