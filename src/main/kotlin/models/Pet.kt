package org.example.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Pet(
    val id: Long? = null,
    val category: Category? = null,
    val name: String,
    @param:JsonProperty("photoUrls")
    @field:JsonProperty("photoUrls")
    val photoUrls: List<String>,
    val tags: List<Tag>? = null,
    val status: String? = null
)