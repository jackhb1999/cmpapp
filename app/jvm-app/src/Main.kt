import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Window(onCloseRequest = ::exitApplication, title = "rustob") {
        MaterialTheme {
            getMain()
        }
//        Screen()
    }
}


@Composable
@Preview
fun Screen() {
    androidx.compose.material3.MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicText("Hello, ${getWorld()}!")
        }
    }
}