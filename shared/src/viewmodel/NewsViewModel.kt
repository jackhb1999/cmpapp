package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import repository.NewsRepository
import uistate.NewsUiState
import java.io.IOException

class NewsViewModel(
    private val repository: NewsRepository,
) : ViewModel() {
    //    val uiState = mutableStateOf(NewsUiState())
    var uiState by mutableStateOf(NewsUiState())
        private set

    private var fetchJob: Job? = null

    fun fetchArticles(category:String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try{
                val newsItems = repository.newsItemsForCategory(category)
                uiState = uiState.copy(newsItems = newsItems)
            }catch (ioe: IOException) {
//                val message = getMessagesFromThrowable(ioe)
//                uiState = uiState.copy(userMessages = listOf(message))
            }
        }
    }


}