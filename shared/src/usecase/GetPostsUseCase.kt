package usecase

import model.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.PostRepository
import util.Constants

class GetPostsUseCase : KoinComponent {

    private val repository by inject<PostRepository>()


    suspend operator fun invoke(page: Int, pageSize: Int): Result<List<Post>> {
        return repository.getFeedPosts(
            userId = Constants.EMPTY_STR,
            pageNumber = page, pageSize = pageSize
        )
    }

}