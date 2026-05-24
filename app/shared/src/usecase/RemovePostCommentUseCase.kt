package usecase

import model.NewCommentParams
import model.PostComment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.PostCommentsRepository
import util.Constants
import kotlin.getValue

class RemovePostCommentUseCase : KoinComponent {
    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(
        postId: String,
        commentId: String,
    ): Result<Boolean> {
        return repository.removeComment(commentId, postId, Constants.EMPTY_STR)
    }
}