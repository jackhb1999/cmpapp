package screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.LoginView
import view.SignUpView

class SignUpScreen : Screen {
    override val key: String
        get() = "注册"

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val size = calculateWindowSizeClass()
        when (size.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                SignUpView(
                    gotoLogin = {
                        navigator?.pop()
                    }
                )
            }

            WindowWidthSizeClass.Medium -> {
                SignUpView(
                    gotoLogin = {
                        navigator?.pop()
                    }
                )
            }

            WindowWidthSizeClass.Expanded -> {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.weight(1f)) {
                        SignUpView(
                            gotoLogin = {
                                navigator?.pop()
                            }
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LoginView(
                            gotoHome = {
                                navigator?.push(HomeScreen(token = "123"))
                            },
                            gotoSignUp = { navigator?.push(SignUpScreen()) }
                        )
                    }
                }
            }
        }

    }

}