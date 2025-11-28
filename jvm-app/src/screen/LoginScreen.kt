package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.LoginView

class LoginScreen : Screen {
    override val key: String
        get() = "登录"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        LoginView(
            gotoHome = {
                navigator?.push(HomeScreen(token = "123"))
            },
            gotoSignUp = { navigator?.push(SignUpScreen()) }
        )
    }
}