package usecase

import model.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.PostRepository
import util.Constants

class GetPostUseCase : KoinComponent {
    private val postRepository: PostRepository by inject()

    suspend operator fun invoke(postId: String): Result<Post> {
        return postRepository.getPost(postId, currentUserId = Constants.EMPTY_STR)
    }
}