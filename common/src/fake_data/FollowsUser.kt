package fake_data

import kotlinx.serialization.builtins.FloatArraySerializer

data class FollowsUser(
    val id:Int,
    val name:String,
    val profileUrl:String,
    val isFollowing: Boolean = false
)

val sampleUsers = listOf(
    FollowsUser(1,"user1","https://example.com/user1"),
    FollowsUser(2,"user2","https://example.com/user2"),
    FollowsUser(3,"user3","https://example.com/user3"),
)
