package ui.screen

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import getWorld
import ui.screenmodel.HomeScreenModel
import ui.screenmodel.State
import ui.view.HomeView


actual val homeScreen: Screen
    get() = HomeScreen()

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<HomeScreenModel>()
        val uiState by screenModel.uiState.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        HomeView()

        LaunchedEffect(uiState) {
            when (val state = uiState) {
                is State.Result -> {
                    println(25)
                }

                is State.Loading -> {
                    println(29)
                }

                is State.NotLogin -> {
                    println(33)
                }
            }
        }
    }
}