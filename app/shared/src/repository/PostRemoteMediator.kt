//package repository
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import model.Post
//import service.PostService
//
//@OptIn(ExperimentalPagingApi::class)
//class PostRemoteMediator(
//    private val service: PostService,
//    private val db:Database
//): RemoteMediator<Int, Post>() {
//
//    override suspend fun initialize(): InitializeAction {
//        // 决定启动时是否强制刷新（可以根据缓存时效判断）
//        return if (db.articleDao().count() == 0) {
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        } else {
//            InitializeAction.SKIP_INITIAL_REFRESH
//        }
//    }
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, Post>
//    ): MediatorResult {
//        return try {
//            val page = when (loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    // 从 DB 查上次加载的最后一页的页码
//                    val lastItem = state.lastItemOrNull()
//                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    db.remoteKeyDao().getKey(lastItem.id)?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                }
//            }
//            val response = api.getArticles(page = page, size = state.config.pageSize)
//            db.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    db.articleDao().clearAll() // 刷新时清空旧数据
//                    db.remoteKeyDao().clearAll()
//                }
//                db.remoteKeyDao().insertAll(response.articles.map {
//                    RemoteKey(articleId = it.id, nextKey = if (response.hasMore) page + 1 else null)
//                })
//                db.articleDao().insertAll(response.articles)
//            }
//            MediatorResult.Success(endOfPaginationReached = !response.hasMore)
//        } catch (e: Exception) {
//            MediatorResult.Error(e)
//        }
//    }
//}