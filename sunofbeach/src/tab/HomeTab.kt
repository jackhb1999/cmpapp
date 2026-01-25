package tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import screen.DetailsScreen
import view.HomeView

object HomeTab : Tab {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        fun goPostDetailFun(postId: Int) {
            navigator?.push(DetailsScreen(postId))
        }
        HomeView(goPostDetail = ::goPostDetailFun)
    }

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 0u,
                title = "Home",
                icon = null
            )
        }
}
