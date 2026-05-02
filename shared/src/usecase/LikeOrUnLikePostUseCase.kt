package usecase

import model.LikeParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.PostLikesRepository
import util.Constants


class LikeOrUnLikePostUseCase : KoinComponent {
    private val repository by inject<PostLikesRepository>()

    suspend operator fun invoke(likePostId: String, isLiked: Boolean): Result<Boolean> {
        val params = LikeParams(postId = likePostId, userId = Constants.EMPTY_STR)
        return if (isLiked) {
            repository.addLike(params)
        } else {
            repository.removeLike(params)
        }
    }

}