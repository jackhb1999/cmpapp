package screen

import tab.Navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class HomeScreen(val token: String?) : Screen {
    @Composable
    override fun Content() {

        Navigation()
        val navigator = LocalNavigator.current
        LaunchedEffect(key1 = token, block = {
            if (token.isNullOrEmpty()) {
                // 处理 token 为空的情况
                navigator?.push(LoginScreen())
            } else {
                // 处理 token 不为空的情况
            }
        })
    }
}

