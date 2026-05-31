import androidx.compose.runtime.Composable

expect fun getWorld(): String

@Composable
expect fun getMain()

// Main.kt 中只允许有 expect


