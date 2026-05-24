package util

import kotlinx.serialization.Serializable

// 没权限操作

// 操作失败

// 已经操作过了

// 先不细化
fun <T : @Serializable Any> error(message: String? = null): Any = Result.failure<T>(Throwable(message))