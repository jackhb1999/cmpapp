package screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import tab.Navigation
import view.FollowsView
import view.ProfileView

class FollowsScreen(val userId: String, val followsType: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        fun goProfileClickFn(userId: String) {
            navigator?.push(ProfileScreen(userId))
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("关注详情页") },
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
            FollowsView(userId = userId, followsType = followsType, onItemClick = ::goProfileClickFn)
        }
    }
}