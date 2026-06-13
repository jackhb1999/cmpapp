package model

import kotlinx.serialization.Serializable

@Serializable
data class PlateTree(
    val id: String,
    val name: String,
    val child: List<PlateTree>
)