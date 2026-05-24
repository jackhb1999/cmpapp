package fake_data

data class Profile(
    val id: Int,
    val name: String,
    val bio: String,
    val profileUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val isOwnProfile: Boolean = false,
    val isFollowing: Boolean = false,
)

val sampleProfiles = listOf(
    Profile(
        id = 1, name = "jack", bio = "Jack's Bio", profileUrl = "https://www.jack.cc/",
        followersCount = 1, followingCount = 1, isOwnProfile = true, isFollowing = false
    ),
    Profile(
        id = 2, name = "Jary", bio = "Jack's Bio", profileUrl = "https://www.jack.cc/",
        followersCount = 1, followingCount = 1, isOwnProfile = true, isFollowing = false
    ),
    Profile(
        id = 3, name = "张三", bio = "张三bio", profileUrl = "https://www.bio.com/",
        followersCount = 1, followingCount = 1, isOwnProfile = true, isFollowing = false
    ),
    Profile(
        id = 4, name = "李四", bio = "lisi", profileUrl = "https://www.bio.com/",
        followersCount = 1, followingCount = 1, isOwnProfile = true, isFollowing = false
    ),
)