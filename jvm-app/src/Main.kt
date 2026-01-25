import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import components.AppBar
import di.authModule
import di.getSharedModules
import di.otherModule
import org.koin.core.context.startKoin
import screen.LoginScreen
import screen.SignUpScreen
import viewmodel.MainActivityViewModel



// 1. 创建自定义 ViewModelStoreOwner
class DesktopViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

fun main() = application {
    startKoin {
        modules(authModule + otherModule + getSharedModules())
    }
    Window(onCloseRequest = ::exitApplication) {
        val owner = remember { DesktopViewModelStoreOwner() }


        val scaffoldState = rememberScaffoldState()

        // 将其提供给 Compose 树
        CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {

            val vm: MainActivityViewModel = viewModel()

            MaterialTheme {
                Navigator(LoginScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}


