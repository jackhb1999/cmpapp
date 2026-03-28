package repository

import model.Post
import model.PostTextParams
import util.Result

interface PostRepository {

    suspend fun createPost(imageUrl:String,postTextParams: PostTextParams): Result<Any>

    suspend fun getFeedPosts(userId: String,pageNumber:Int,pageSize:Int): Result<List<Post>>

    suspend fun getPostsByUser(postsOwnerId: String,currentUserId: String,pageNumber: Int,pageSize: Int): Result<List<Post>>

    suspend fun getPost(postId:String,currentUserId: String): Result<Post>

    suspend fun deletePost(postId: String):Result<Any>
}