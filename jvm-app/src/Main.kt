import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


// 1. 创建自定义 ViewModelStoreOwner
//class DesktopViewModelStoreOwner : ViewModelStoreOwner {
//    override val viewModelStore = ViewModelStore()
//}

//fun main() = application {
//    startKoin {
//        modules(authModule + otherModule + getSharedModules())
//    }
//    Window(onCloseRequest = ::exitApplication) {
//        val owner = remember { DesktopViewModelStoreOwner() }
//
//
//        val scaffoldState = rememberScaffoldState()
//
//        // 将其提供给 Compose 树
//        CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
//
//            val vm: MainActivityViewModel = viewModel()
//
//            MaterialTheme {
//                Navigator(LoginScreen()) { navigator ->
//                    SlideTransition(navigator)
//                }
//            }
//        }
//    }
//}


fun main() = application {
    getDI()
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            getMain()
        }
    }
}


