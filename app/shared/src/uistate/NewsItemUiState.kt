package uistate

data class NewsItemUiState (
    val title:String,
    val body:String,
    val bookmarked:Boolean = false
)