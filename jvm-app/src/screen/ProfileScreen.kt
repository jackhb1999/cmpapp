package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fake_data.Post
import view.EditProfileView
import view.ProfileView

data class ProfileScreen(val userId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val postClick = fun(postId: Int) {
            navigator?.push(PostDetailScreen(postId))
        }
        ProfileView(
            userId = userId,
            onPostClick = postClick,
            onCommentClick = postClick,
            onButtonClick = { navigator?.push(EditProfileScreen(userId)) }
        )
    }
}