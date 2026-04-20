package util

interface PagingManage<Model> {

    suspend fun loadItems()

    fun reset()
}

class DefaultPagingManage<Model>(
    private val onRequest: suspend (page: Int) -> Result<List<Model>>,
    private val onSuccess: (items: List<Model>, page: Int) -> Unit,
    private val onError: (cause: String, page: Int) -> Unit,
    private val onLoadStateChange: (isLoading: Boolean) -> Unit
) : PagingManage<Model> {

    private var currentPage = Constants.INITIAL_PAGE_NUMBER
    private var isLoading = false

    override suspend fun loadItems() {
        if (isLoading) return
        isLoading = true
        onLoadStateChange(true)
        val result = onRequest(currentPage)
        isLoading = false
        onLoadStateChange(false)

        when (result) {
            is Result.Success -> {
                onSuccess(result.data!!, currentPage)
                currentPage += 1
            }

            is Result.Error<*> -> {
                onError(result.message ?: Constants.UNEXPECTED_ERROR_MESSAGE, currentPage)
            }
        }

    }

    override fun reset() {
        currentPage = Constants.INITIAL_PAGE_NUMBER
    }
}