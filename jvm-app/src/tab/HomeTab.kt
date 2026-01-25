package tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import screen.DetailsScreen
import screen.FirstScreen
import screen.ProfileScreen
import view.HomeView

object HomeTab : Tab {
    @Composable
    override fun Content() {

        Navigator(
            FirstScreen()
        )
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
