import androidx.compose.runtime.Composable
import di.authModule
import di.getSharedModules
import di.otherModule

expect fun getWorld(): String

@Composable
expect fun getMain()

expect fun getDI(): Unit


