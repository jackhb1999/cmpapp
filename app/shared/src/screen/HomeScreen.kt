package screen

import tab.Navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.MainActivityUiState
import viewmodel.MainActivityViewModel

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel: MainActivityViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current
        Navigation()

        LaunchedEffect(uiState) {
            when (val state = uiState) {
                is MainActivityUiState.Success -> {
                    println(25)
                    navigator?.popUntilRoot()
                }

                is MainActivityUiState.Loading -> {
                    println(29)
                }

                is MainActivityUiState.Error -> {
                    println(33)
                    navigator?.popUntilRoot()
                    navigator?.push(LoginScreen())
                }
            }
        }
    }
}

