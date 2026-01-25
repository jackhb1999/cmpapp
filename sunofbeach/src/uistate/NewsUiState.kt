package uistate

data class NewsUiState(
    val isSignedIn: Boolean= false,
    val isPremium: Boolean = false,
    val newsItems:List<NewsItemUiState> = listOf(),
//    val userMessages:List<Message> = listOf()
)

// 派生属性
val NewsUiState.canBookmarkNews: Boolean get() = isSignedIn && isPremium
