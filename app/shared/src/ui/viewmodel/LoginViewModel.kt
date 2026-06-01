package ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.SignParams
import org.lighthousegames.logging.logging
import service.UserService


class LoginViewModel(
    private val userService: UserService
) : ViewModel() {

    companion object {
        val log = logging()
    }


    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signIn() {
        viewModelScope.launch {
            val result = userService.signIn(
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
data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var id: String = "",
)
