package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: Long? = null,
    val category: Category? = null,
    val name: String,
    @SerialName("photoUrls")
    val photoUrls: List<String>,
    val tags: List<Tag>? = null,
    val status: String? = null
) {

    @Serializable
    data class Category(
        val id: Long? = null,
        val name: String? = null
    )

    @Serializable
    data class Tag(
        val id: Long? = null,
        val name: String? = null
    )

    enum class PetStatus(val status: String) {
        AVAILABLE("available"),
        PENDING("pending"),
        SOLD("sold")
    }
}