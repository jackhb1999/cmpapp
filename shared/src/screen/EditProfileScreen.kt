package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import view.EditProfileView

class EditProfileScreen(val userId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        EditProfileView(
            userId = userId,
            onUploadSuccess = { navigator?.pop() }
        )
    }

}
