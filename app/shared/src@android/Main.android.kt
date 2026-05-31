import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screen.HomeScreen

actual fun getWorld() = "Android World"

@Composable
actual fun getMain() {
    Navigator(HomeScreen()) { navigator ->
        SlideTransition(navigator)
    }
}

//actual fun getDI() {
//    startKoin {
//        modules( getSharedModules() + appModule())
//    }
//}