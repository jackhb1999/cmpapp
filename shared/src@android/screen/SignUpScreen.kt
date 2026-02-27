package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import view.SignUpView

@Composable
actual fun SignUpScreenContext() {
    val navigator = LocalNavigator.current

    SignUpView(
        gotoLogin = {
            navigator?.pop()
        }
    )
}
