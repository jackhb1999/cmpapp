package fake_data

data class Post(
    val id:Int,
    val text:String,
    val imageUrl:String,
    val createdAt:String,
    val likeCount:Int,
    val commentCount:Int,
    val authorId:Int,
    val authorName:String,
    val authorImage:String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false
)

val samplePosts = listOf(
    Post(1,"post1","https://example.com/post1","2023-01-01",10,5,1,"user1","https://example.com/user1"),
    Post(2,"post2","https://example.com/post2","2023-01-02",20,10,2,"user2","https://example.com/user2"),
    Post(3,"post3","https://example.com/post3","2023-01-03",30,15,3,"user3","https://example.com/user3"),
)
