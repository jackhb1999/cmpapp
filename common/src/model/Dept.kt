package model

import kotlinx.serialization.Serializable

@Serializable
data class DeptParams(
    val deptId: Int?,
    val parentId: Int?,
    val deptName: String?,
    val status: String?,
)