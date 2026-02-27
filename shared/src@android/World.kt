import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.authModule
import di.getSharedModules
import di.otherModule
import org.koin.core.context.startKoin
import screen.LoginScreen

actual fun getWorld() = "Android World"

@Composable
actual fun getMain() {
    Navigator(LoginScreen()) { navigator ->
        SlideTransition(navigator)
    }
}

actual fun getDI() {
    startKoin {
        modules(authModule + otherModule + getSharedModules())
    }
}