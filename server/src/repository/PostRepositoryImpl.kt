package com.hb.repository

import com.hb.dao.follows.FollowsDao
import com.hb.dao.post.PostDao
import com.hb.dao.post.PostRow
import com.hb.dao.post_likes.PostLikesDao
import com.hb.dao.user.UserDao
import io.ktor.http.HttpStatusCode
import model.Post
import model.PostTextParams
import repository.PostRepository
import util.Result

class PostRepositoryImpl(
    private val postDao: PostDao,
    private val followsDao: FollowsDao,
    private val postLikesDao: PostLikesDao
) : PostRepository {
    override suspend fun createPost(imageUrl: String, postTextParams: PostTextParams): Result<Any> {
        val postIsCreated = postDao.createPost(
            caption = postTextParams.caption,
            imageUrl = imageUrl,
            userId = postTextParams.userId
        )
        return if (postIsCreated) {
            Result.Success(msg = "Post created")
        } else {
            Result.Error(
                code = HttpStatusCode.InternalServerError,
                message = "Post creation failed"
            )
        }
    }

    override suspend fun getFeedPosts(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<Post>> {
        val followingUsers = followsDao.getFollowing(userId)
        val followingUsersList = ArrayList<String>()
        followingUsersList.addAll(followingUsers)
        followingUsersList.add(userId)
        val postsRows = postDao.getFeedsPost(userId, followingUsersList, pageNumber, pageSize)
        val posts = postsRows.map {
            toPost(
                postRow = it,
                isPostLiked = postLikesDao.isPostLiked(postId = it.postId, userId = userId),
                isOwnPost = it.userId == userId
            )
        }
        return Result.Success(posts)
    }

    override suspend fun getPostsByUser(
        postsOwnerId: String,
        currentUserId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<Post>> {
       val postsRows = postDao.getPostByUser(userId = postsOwnerId, pageNumber = pageNumber, pageSize = pageSize)
        val posts = postsRows.map {
            toPost(
                postRow = it,
                isPostLiked = postLikesDao.isPostLiked(postId = it.postId, userId = currentUserId),
                isOwnPost = it.userId == currentUserId
            )
        }
        return Result.Success(posts)
    }

    override suspend fun getPost(postId: String, currentUserId: String): Result<Post> {
        val postRow = postDao.getPost(postId)
        return if (postRow != null) {
            val isPostLiked = postLikesDao.isPostLiked(postId, currentUserId)
            val isOwnPost = postRow.userId == currentUserId
            Result.Success<Post>(data = toPost(postRow, isPostLiked, isOwnPost))
        } else {
            Result.Error(message = "not get post")
        }
    }

    override suspend fun deletePost(postId: String): Result<Any> {
        val postIsDeleted = postDao.deletePost(postId)
        return if (postIsDeleted) {
            Result.Success(msg = "deleted Post '${postId}' successfully")
        } else {
            Result.Error(
                code = HttpStatusCode.InternalServerError,
                message = "fail"
            )
        }
    }

    private fun toPost(postRow: PostRow, isPostLiked: Boolean, isOwnPost: Boolean): Post {
        return Post(
            postId = postRow.postId,
            caption = postRow.caption,
            imageUrl = postRow.imageUrl,
            likesCount = postRow.likesCount,
            commentsCount = postRow.commentsCount,
            userId = postRow.userId,
            username = postRow.username,
            userImageUrl = postRow.userImageUrl,
            isLiked = isPostLiked,
            isOwnPost = isOwnPost,
        )
    }
}