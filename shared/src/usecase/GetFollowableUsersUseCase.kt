package usecase

import model.FollowUserData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.FollowsRepository
import util.Constants
import util.Result

class GetFollowableUsersUseCase : KoinComponent {
    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(): Result<List<FollowUserData>>{
        return repository.getFollowingSuggestions(Constants.EMPTY_STR)
    }
}