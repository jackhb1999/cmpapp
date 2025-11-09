package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.SignUpView

class SignUpScreen: Screen {
    override val key: String
        get() = "注册"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        SignUpView(
            gotoLogin = {
                navigator?.pop()
            }
        )
    }

}