package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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


    val uiState: StateFlow<MainActivityUiState> = dataStore.data.catch { e ->
        // 处理读取 DataStore 时的异常（例如文件损坏）
        // 这里可以 emit 一个错误状态，或者 emit 一个默认空对象
        e.printStackTrace()
    }
        .map { userSettings ->
            // 将 DataStore 的数据映射为 UI 状态
            // 注意：这里不需要 copy，直接传进去即可，除非你需要转换字段
            if (userSettings.token.isEmpty()) {
                MainActivityUiState.Error
            } else {
                MainActivityUiState.Success(userSettings)
            }
        }
        .stateIn(
            scope = viewModelScope,
            // WhileSubscribed: 当界面可见时读取，界面销毁 5秒后停止读取（节省资源）
            started = SharingStarted.WhileSubscribed(5000),
            // 初始值为 Loading，直到 DataStore 发出第一个数据
            initialValue = MainActivityUiState.Loading
        )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val data: UserSettingsData) : MainActivityUiState

    object Error : MainActivityUiState
}