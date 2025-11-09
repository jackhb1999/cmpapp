import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.authModule
import di.getSharedModules
import org.koin.core.context.startKoin
import screen.LoginScreen
import screen.SignUpScreen


// 1. 创建自定义 ViewModelStoreOwner
class DesktopViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

fun main() = application {
    startKoin {
        modules(authModule + getSharedModules())
    }
    Window(onCloseRequest = ::exitApplication) {
        val owner = remember { DesktopViewModelStoreOwner() }


        val scaffoldState = rememberScaffoldState()


        // 将其提供给 Compose 树
        CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
            MaterialTheme {
                Navigator(LoginScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}


