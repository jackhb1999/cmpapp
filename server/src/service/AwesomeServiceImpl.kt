package com.hb.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import service.AwesomeService

class AwesomeServiceImpl : AwesomeService {
    override fun getNews(city: String): Flow<String> {
        return flow {
            emit("Today is 23 degrees!")
            emit("Harry Potter is in $city!")
            emit("New dogs cafe has opened doors to all fluffy customers!")
        }
    }

    override suspend fun daysUntilStableRelease(): Int {
        return 0
    }
}