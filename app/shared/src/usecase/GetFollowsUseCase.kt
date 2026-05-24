package usecase

import model.FollowUserData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.FollowsRepository
import util.Constants

class GetFollowsUseCase : KoinComponent {
    private val followsRepository: FollowsRepository by inject()

    suspend operator fun invoke(
        userId: String = Constants.EMPTY_STR,
        page: Int,
        pageSize: Int,
        followsType: Int
    ): Result<List<FollowUserData>> {
        return if (followsType == 1) {
            followsRepository.getFollowers(userId, page, pageSize)
        } else if (followsType == 2) {
            followsRepository.getFollowing(userId, page, pageSize)
        } else {
            Result.failure(NotImplementedError())
        }
    }
}