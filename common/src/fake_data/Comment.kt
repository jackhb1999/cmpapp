package fake_data

data class Comment(
    val id: String,
    val comment: String,
    val date: String,
    val authorName: String,
    val authorImageUrl: String?,
    val authorId: String,
    val postId: String,
)

val sampleComments = listOf(
    Comment(
        id = "comment1",
        date = "2025-11-30",
        comment = "测试评论\n换行",
        authorName = sampleUsers[0].name,
        authorImageUrl = sampleUsers[0].imageUrl,
        authorId = sampleUsers[0].id,
        postId = samplePosts[0].postId,
    ),
    Comment(
        id = "comment2",
        date = "2025-11-30",
        comment = "测试评论2\n换行",
        authorName = sampleUsers[0].name,
        authorImageUrl = sampleUsers[0].imageUrl,
        authorId = sampleUsers[0].id,
        postId = samplePosts[0].postId
    ),
)