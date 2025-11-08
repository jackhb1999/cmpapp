package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CountViewModel  : ViewModel() {

    // ui 中的状态
    var count by mutableStateOf(0)
        private set

    // ui 中的行为
    fun updateCount() {
        count++
    }

}