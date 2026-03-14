package screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import tab.Navigation
import view.FollowsView
import view.ProfileView

class FollowsScreen(val userId: Int, val followsType: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        fun goProfileClickFn(userId: Int) {
            navigator?.push(ProfileScreen(userId))
        }
        FollowsView(userId = userId, followsType = followsType, onItemClick = ::goProfileClickFn)


    }
}