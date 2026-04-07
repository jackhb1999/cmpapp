import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.authModule
import di.getSharedModules
import di.otherModule
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.startKoin
import screen.HomeScreen
import screen.LoginScreen
import viewmodel.MainActivityUiState
import viewmodel.MainActivityViewModel

actual fun getWorld() = "JVM World"

@Composable
actual fun getMain() {
    val viewModel: MainActivityViewModel = koinViewModel()
    LaunchedEffect(key1 = null, block = {
        viewModel.readDataStore()
        println("22" + viewModel.userSettingsData.token)
    })

    var screen = LoginScreen()

    Navigator(screen) { navigator ->
        SlideTransition(navigator)
    }
}


actual fun getDI() {
    startKoin {
        modules(authModule + otherModule + getSharedModules())
    }
}