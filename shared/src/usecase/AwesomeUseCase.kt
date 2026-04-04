package usecase

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import service.AwesomeService
import service.AwesomeServiceImpl

class AwesomeUseCase : KoinComponent {
    private val service: AwesomeServiceImpl by inject()
    fun getNews(city: String): Flow<String> = service.getNews(city)

    suspend fun daysUntilStableRelease(): Int = service.daysUntilStableRelease()
}