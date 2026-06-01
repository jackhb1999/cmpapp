package ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.SignParams
import service.UserService
import ui.viewmodel.LoginViewModel.Companion.log

class SignUpViewModel(
    private val userService: UserService
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set


    fun signUp() {
        viewModelScope.launch {
            val result =    userService.signUp(
                SignParams(
                    email = uiState.email,
                    password = uiState.password
                )
            ).toResult()
            when {
                result.isFailure -> {
                    log.info { "Sign In Failed" }

                }

                result.isSuccess -> {
                    result.getOrNull()?.let { user ->
                        uiState = uiState.copy(
                            email = user.email,
                            id = user.id
                        )
                    }
                }
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
data class SignUpUiState(
    var email: String = "",
    var password: String = "",
    var id: String = "",
)
