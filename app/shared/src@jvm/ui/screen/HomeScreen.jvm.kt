package ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import androidx.compose.runtime.livedata.observeAsState
import ui.screenmodel.HomeScreenModel



actual val homeScreen: Screen
    get() = HomeScreen()

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.observeAsState()
        val navigator = LocalNavigator.current

        LaunchedEffect(key1 = Unit, block = { screenModel.init() })

        LaunchedEffect(state) {
            when (val state = state) {
                is HomeScreenModel.State.Result -> {


                }

//                is MainActivityUiState.Success -> {
//                    println(25)
//                    navigator?.popUntilRoot()
//                }
//
//                is MainActivityUiState.Loading -> {
//                    println(29)
//                }
//
//                is MainActivityUiState.Error -> {
//                    println(33)
//                    navigator?.popUntilRoot()
//                    navigator?.push(LoginScreen())
//                }
                HomeScreenModel.State.Error -> {

                }
                HomeScreenModel.State.Loading -> {

                }

                else -> {

                }
            }
        }
    }
}