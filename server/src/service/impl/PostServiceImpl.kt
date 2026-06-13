package com.hb.service.impl

import com.hb.dao.post.PostDao
import model.PageParams
import model.Post
import model.PostParams
import service.PostService
import util.ActionResult
import util.send

class PostServiceImpl(
    private val postDao: PostDao
) : PostService {
    override suspend fun createPost(postParams: PostParams): ActionResult<Boolean> {
        val result = Result.success(postDao.createPost(postParams))
        return result.send()
    }

    override suspend fun getPosts(params: PageParams): ActionResult<List<Post>> {
        val postRows = postDao.getPost(params)
        val posts = postRows.map {
            Post(
                postId = it.postId,
                content = it.content,
                url = it.url,
                plateId = it.plateId,
                plateName = it.plateName,
                likesCount = it.likesCount,
                notLikesCount = it.notLikesCount,
                commentsCount = it.commentsCount,
                userId = it.userId,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
        val result = Result.success(posts)
        return result.send()
    }
}