package fake_data

import kotlinx.serialization.builtins.FloatArraySerializer

data class FollowsUser(
    val id: Int,
    val name: String,
    val bio: String = "hello world!",
    val profileUrl: String,
    val isFollowing: Boolean = false
)

val sampleUsers = listOf(
    FollowsUser(
        id = 1,
        name = "user1",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    FollowsUser(
        id = 2,
        name = "user2",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    FollowsUser(
        id = 3,
        name = "user3",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    FollowsUser(
        id = 4,
        name = "user4",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    FollowsUser(
        id = 5,
        name = "user5",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
    FollowsUser(
        id = 6,
        name = "user6",
        profileUrl = "https://p26-passport.byteacctimg.com/img/user-avatar/2bcd7dcfed80e989872fa060f838954f~40x40.awebp"
    ),
)
