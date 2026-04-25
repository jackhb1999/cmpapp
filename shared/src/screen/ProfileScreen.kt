package screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.ProfileView

data class ProfileScreen(val userId: String) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("ProfileScreen详情页") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                // 处理返回按钮点击事件
                                navigator?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "返回"
                            )
                        }
                    }
                )
            }
        ) {
            val postClick = fun(postId: String) {
                navigator?.push(PostDetailScreen(postId))
            }
            ProfileView(
                userId = userId,
                onPostClick = postClick,
                onCommentClick = postClick,
                onButtonClick = { navigator?.push(EditProfileScreen(userId)) },
                onFollowersClick = { navigator?.push(FollowsScreen(userId, 1)) },
                onFollowingClick = { navigator?.push(FollowsScreen(userId, 2)) },
            )
        }
    }
}