package model

import kotlinx.serialization.Serializable

@Serializable
data class PageParams(
    val pageNumber: Int,
    val pageSize: Int,
) {
    fun limit(): Int {
        return pageSize
    }

    fun offset(): Long {
        return ((pageNumber - 1) * pageSize).toLong()
    }
}