package service

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc
import kotlin.Result as Result1


@Rpc
interface AwesomeService {
    fun getNews(city: String): Flow<String>

    suspend fun daysUntilStableRelease(): Int
}