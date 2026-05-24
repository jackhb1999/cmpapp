package usecase

import model.FollowUserData
import model.NewCommentParams
import model.PostComment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.FollowsRepository
import repository.PostCommentsRepository
import util.Constants
import kotlin.getValue

class AddPostCommentUseCase : KoinComponent {

    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(
        postId: String,
        content: String
    ): Result<PostComment> {
        val params = NewCommentParams(
            postId = postId,
            content = content,
            userId = Constants.EMPTY_STR,
        )
        return repository.addComment(params)
    }
}