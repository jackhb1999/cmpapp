package usecase

import io.github.oshai.kotlinlogging.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.FollowsRepository
import util.Constants

private val logger = KotlinLogging.logger {}

class FollowOrUnfollowUseCase : KoinComponent {

    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(
        followedUserId: String,
        shouldFollow: Boolean,
    ): Result<Boolean> {
        logger.info { "FollowOrUnfollowUseCase $followedUserId $shouldFollow" }
        return if (shouldFollow) {
            repository.followUser(follower = Constants.EMPTY_STR, followedUserId)
        } else {
            repository.unfollowUser(follower = Constants.EMPTY_STR, followedUserId)
        }
    }
}