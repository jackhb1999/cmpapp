package ui.screenmodel

import androidx.datastore.core.DataStore
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.livedata.LiveScreenModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import model.User


class HomeScreenModel(
    private val dataStore: DataStore<User>
) : LiveScreenModel<HomeScreenModel.State>(State.Loading) {

    sealed class State {
        object Loading : State()
        data class Result(val user: User) : State()
        object Error : State()
    }

    fun init(){
        dataStore.data.catch { e ->
            // 处理读取 DataStore 时的异常（例如文件损坏）
            // 这里可以 emit 一个错误状态，或者 emit 一个默认空对象
            e.printStackTrace()
        }
            .map { userSettings ->
                // 将 DataStore 的数据映射为 UI 状态
                // 注意：这里不需要 copy，直接传进去即可，除非你需要转换字段
                if (userSettings.id.isEmpty()) {
                    State.Error
                } else {
                    State.Result(userSettings)
                }
            }
            .stateIn(
                scope = screenModelScope,
                // WhileSubscribed: 当界面可见时读取，界面销毁 5秒后停止读取（节省资源）
                started = SharingStarted.WhileSubscribed(5000),
                // 初始值为 Loading，直到 DataStore 发出第一个数据
                initialValue = State.Loading
            )
    }

}