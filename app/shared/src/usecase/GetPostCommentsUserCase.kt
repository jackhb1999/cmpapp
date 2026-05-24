package usecase;

import model.PostComment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.PostCommentsRepository

class GetPostCommentsUserCase: KoinComponent {
    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(
        postId: String,
        page: Int,
        pageSize: Int,
    ): Result<kotlin.collections.List<PostComment>> {
        return repository.getPostComments(
            postId = postId,
            pageNumber = page, pageSize = pageSize
        )
    }

}
