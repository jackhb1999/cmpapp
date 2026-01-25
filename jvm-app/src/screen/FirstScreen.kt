package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.HomeView

class FirstScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        fun goPostDetailFun(postId: Int) {
            navigator?.push(DetailsScreen(postId))
        }

        fun goProfileClickFn(userId: Int) {
            navigator?.push(ProfileScreen(userId))
        }
        HomeView(goPostDetail = ::goPostDetailFun, goProfileClick = ::goProfileClickFn)
    }
}