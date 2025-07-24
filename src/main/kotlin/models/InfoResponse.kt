package org.example.models

import kotlinx.serialization.Serializable

@Serializable
data class InfoResponse(
    val code: Int,
    val type: String,
    val message: String
)
