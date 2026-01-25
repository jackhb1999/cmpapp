package fake_data

data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String,
    val createdAt: String,
    val likeCount: Int,
    val commentCount: Int,
    val authorId: Int,
    val authorName: String,
    val authorImage: String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false
)

val samplePosts = listOf(
    Post(
        1,
        "post1",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        "2023-01-01",
        10,
        5,
        1,
        "user1",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    Post(
        2,
        "post2",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        "2023-01-02",
        20,
        10,
        2,
        "user2",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    Post(
        3,
        "post3",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        "2023-01-03",
        30,
        15,
        3,
        "user3",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
)
