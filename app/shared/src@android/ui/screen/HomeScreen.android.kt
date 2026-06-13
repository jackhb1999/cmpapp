package ui.screen

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.screenmodel.HomeScreenModel
import androidx.compose.runtime.livedata.observeAsState
import getWorld
import ui.view.HomeView


actual val homeScreen: Screen
    get() = HomeScreen()


class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.observeAsState()
        val navigator = LocalNavigator.current

        BasicText("Hello, ${getWorld()}!")

        LaunchedEffect(key1 = Unit, block = { screenModel.init() })

        LaunchedEffect(state) {
            when (val state = state) {
                is HomeScreenModel.State.Result -> {
                    navigator?.popUntilRoot()
                }

                HomeScreenModel.State.Error -> {
                    navigator?.popUntilRoot()
                    navigator?.push(LoginScreen())
                }
                HomeScreenModel.State.Loading -> {

                }

                else -> {

                }
            }
        }
    }
}
