package service

import kotlinx.rpc.annotations.Rpc
import model.PageParams
import model.Post
import model.PostParams
import util.ActionResult

@Rpc
interface PostService {

    // 创建帖子
    suspend fun createPost(postParams: PostParams): ActionResult<Boolean>

    // 获取列表
    suspend fun getPosts(params: PageParams): ActionResult<List<Post>>

}