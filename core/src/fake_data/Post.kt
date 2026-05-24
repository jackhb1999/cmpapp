package fake_data

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import model.Post

//data class Post(
//    val id: String,
//    val text: String,
//    val imageUrl: String,
//    val createdAt: String,
//    val likeCount: Int,
//    val commentCount: Int,
//    val authorId: Int,
//    val authorName: String,
//    val authorImage: String,
//    val isLiked: Boolean = false,
//    val isOwnPost: Boolean = false
//)

val samplePosts = listOf(
    Post(
        "1",
        "post1",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        LocalDateTime.parse("2026-04-02T00:00:00"),
        10,
        5,
        "1",
        "user1",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        isLiked = false,
        isOwnPost = false
    ),
    Post(
        "2",
        "post2",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        LocalDateTime.parse("2026-04-03T00:00:00"),
        20,
        10,
        "2",
        "user2",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        isLiked = false,
        isOwnPost = false
    ),
    Post(
        "3",
        "post3",
        "https://lf9-dp-fe-cms-tos.byteorg.com/obj/bit-cloud/23e5d313c2c3a66d4ca806007.png",
        LocalDateTime.parse("2026-04-04T00:00:00"),
        30,
        15,
        "3",
        "user3",
        "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        isLiked = false,
        isOwnPost = false
    ),
)
