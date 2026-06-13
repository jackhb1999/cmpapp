package repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.io.IOException
import model.PageParams
import model.Post
import org.koin.core.KoinApplication.Companion.init
import service.PostService

class PostPagingSource(
    val postService: PostService,
) : PagingSource<Int, Post>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Post> {

        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = postService.getPosts(PageParams(nextPageNumber, params.loadSize))
            val toResult = response.toResult()
            when {
                toResult.isSuccess -> {
                    return LoadResult.Page(
                        data = toResult.getOrThrow(),
                        prevKey = null, // Only paging forward.
                        nextKey = nextPageNumber + 1
                    )
                }

                toResult.isFailure -> {
                    return LoadResult.Error(Throwable(toResult.exceptionOrNull()?.message))
                }
            }

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        }
        return LoadResult.Error(Throwable("未查询到内容"))
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}