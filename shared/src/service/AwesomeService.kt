package service

import common.KtorRpc
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.withService
import org.koin.core.component.KoinComponent

internal class AwesomeServiceImpl : AwesomeService, KtorRpc() {
    val service = rpcClient.withService<AwesomeService>()

     override fun getNews(city: String): Flow<String> = service.getNews(city)

     override suspend fun daysUntilStableRelease(): Int = service.daysUntilStableRelease()

}