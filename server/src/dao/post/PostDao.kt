package com.hb.dao.post

import model.PageParams
import model.PostParams

interface PostDao {

    suspend fun createPost(postParams: PostParams): Boolean

    suspend fun getPost(pageParams: PageParams): List<PostRow>

}