package fake_data

import kotlinx.serialization.builtins.FloatArraySerializer
import model.FollowUserData

//data class FollowsUser(
//    val id: Int,
//    val name: String,
//    val bio: String = "hello world!",
//    val profileUrl: String,
//    val isFollowing: Boolean = false
//)

val sampleUsers = listOf(
    FollowUserData(
        id = "1",
        name = "user1",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
    FollowUserData(
        id = "2",
        name = "user2",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
    FollowUserData(
        id = "3",
        name = "user3",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
    FollowUserData(
        id = "4",
        name = "user4",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
    FollowUserData(
        id = "5",
        name = "user5",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
    FollowUserData(
        id = "6",
        name = "user6",
        imageUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp",
        bio = "",
        isFollowing = false
    ),
)
