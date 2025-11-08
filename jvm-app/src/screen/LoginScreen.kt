package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.LoginView

class LoginScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        LoginView(goAway = { navigator?.push(HomeScreen())})
    }
}