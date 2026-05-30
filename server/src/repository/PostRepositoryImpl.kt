//package com.hb.repository
//
//import com.hb.dao.follows.FollowsDao
//import com.hb.dao.post.PostDao
//import com.hb.dao.post.PostRow
//import com.hb.dao.post_likes.PostLikesDao
//import io.ktor.http.HttpStatusCode
//import model.Post
//import model.PostTextParams
//import repository.PostRepository
//
//
//class PostRepositoryImpl(
//    private val postDao: PostDao,
//    private val followsDao: FollowsDao,
//    private val postLikesDao: PostLikesDao
//) : PostRepository {
//    override suspend fun createPost(imageUrl: String, postTextParams: PostTextParams): Result<Boolean> {
//        val postIsCreated = postDao.createPost(
//            caption = postTextParams.caption,
//            imageUrl = imageUrl,
//            userId = postTextParams.userId
//        )
//        return if (postIsCreated) {
//            Result.success(true)
//        } else {
//            throw Throwable("帖子已经存在")
//        }
//    }
//
//    override suspend fun getFeedPosts(
//        userId: String,
//        pageNumber: Int,
//        pageSize: Int
//    ): Result<List<Post>> {
//        val followingUsers = followsDao.getFollowing(userId)
//        val followingUsersList = ArrayList<String>()
//        followingUsersList.addAll(followingUsers)
//        followingUsersList.add(userId)
//        val postsRows = postDao.getFeedsPost(userId, followingUsersList.distinct(), pageNumber, pageSize)
//        val posts = postsRows.map {
//            toPost(
//                postRow = it,
//                isPostLiked = postLikesDao.isPostLiked(postId = it.postId, userId = userId),
//                isOwnPost = it.userId == userId
//            )
//        }
//        return Result.success(posts)
//    }
//
//    override suspend fun getPostsByUser(
//        postsOwnerId: String,
//        currentUserId: String,
//        pageNumber: Int,
//        pageSize: Int
//    ): Result<List<Post>> {
//        val postsRows = postDao.getPostByUser(userId = postsOwnerId, pageNumber = pageNumber, pageSize = pageSize)
//        val posts = postsRows.map {
//            toPost(
//                postRow = it,
//                isPostLiked = postLikesDao.isPostLiked(postId = it.postId, userId = currentUserId),
//                isOwnPost = it.userId == currentUserId
//            )
//        }
//        return Result.success(posts)
//    }
//
//    override suspend fun getPost(postId: String, currentUserId: String): Result<Post> {
//        val postRow = postDao.getPost(postId)
//        return if (postRow != null) {
//            val isPostLiked = postLikesDao.isPostLiked(postId, currentUserId)
//            val isOwnPost = postRow.userId == currentUserId
//            Result.success<Post>(toPost(postRow, isPostLiked, isOwnPost))
//        } else {
//            throw Throwable("找不到帖子")
//        }
//    }
//
//    override suspend fun deletePost(postId: String): Result<Boolean> {
//        val postIsDeleted = postDao.deletePost(postId)
//        return if (postIsDeleted) {
//            Result.success(true)
//        } else {
//          throw Throwable("帖子已删除")
//        }
//    }
//
//    private fun toPost(postRow: PostRow, isPostLiked: Boolean, isOwnPost: Boolean): Post {
//        return Post(
//            postId = postRow.postId,
//            caption = postRow.caption,
//            imageUrl = postRow.imageUrl,
//            likesCount = postRow.likesCount,
//            commentsCount = postRow.commentsCount,
//            userId = postRow.userId,
//            username = postRow.username,
//            userImageUrl = postRow.userImageUrl,
//            isLiked = isPostLiked,
//            isOwnPost = isOwnPost,
//            createdAt = postRow.createdAt,
//        )
//    }
//}