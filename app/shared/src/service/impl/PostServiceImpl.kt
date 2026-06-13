package service.impl

import common.KtorRpc
import kotlinx.rpc.withService
import model.PageParams
import model.Post
import model.PostParams
import service.PostService
import service.UserService
import util.ActionResult

internal class PostServiceImpl : PostService, KtorRpc() {
    val service = rpcClient.withService<PostService>()

    override suspend fun createPost(postParams: PostParams): ActionResult<Boolean> = service.createPost(postParams)

    override suspend fun getPosts(params: PageParams): ActionResult<List<Post>> = service.getPosts(params)
}