import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screen.HomeScreen

actual fun getWorld() = "JVM World ok"

@Composable
actual fun getMain() {
    Navigator(HomeScreen()) { navigator ->
        SlideTransition(navigator)
    }

}


//actual fun getDI() {
//    startKoin {
//        modules(getSharedModules() + appModule())
//    }
//}