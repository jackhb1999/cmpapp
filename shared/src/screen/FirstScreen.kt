package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.HomeView

class FirstScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val postClick = fun(postId: String) {
            navigator?.push(PostDetailScreen(postId))
        }

        fun goProfileClickFn(userId: String) {
            navigator?.push(ProfileScreen(userId))
        }
        HomeView(goPostDetail = postClick, goProfileClick = ::goProfileClickFn, )
    }
}