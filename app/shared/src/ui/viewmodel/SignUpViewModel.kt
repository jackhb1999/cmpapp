package ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.SignParams
import service.UserService

class SignUpViewModel(
    private val userService: UserService
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set


    fun signUp() {
        viewModelScope.launch {

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
)
