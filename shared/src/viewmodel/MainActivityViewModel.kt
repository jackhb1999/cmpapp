package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.UserSettingsData
import usecase.SignInUseCase
import usecase.UserSettingUseCase

class MainActivityViewModel(
    dataStore: DataStore<UserSettingsData>,
    private val userSettingUseCase: UserSettingUseCase
) : ViewModel() {

    // todo! 不懂什么意思
    val uiState: StateFlow<MainActivityUiState> = dataStore.data.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )

    var userSettingsData by mutableStateOf(UserSettingsData())
        private set

    fun readDataStore() {
        viewModelScope.launch {
            println(36)
            val userSettings = userSettingUseCase()
            userSettings?.let {
                userSettingsData.copy(
                    id = it.id,
                    name = it.name,
                    bio = it.bio,
                    avatar = it.avatar,
                    token = it.token,
                    followersCount = it.followersCount,
                    followingCount = it.followingCount,
                )
            }
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val data: UserSettingsData) : MainActivityUiState
}