package ru.akella.cryptocoin.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("credit_count")
    val creditCount: Int,
    val elapsed: Int,
    @SerialName("error_code")
    val errorCode: Int,
    @SerialName("error_message")
    val errorMessage: String,
    val timestamp: String
)