package ru.akella.cryptocoin.data.response.exchangedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeDetailsData(
    val id: Long,
    val name: String,
    val slug: String,
    val logo: String,
    val description: String,
    @SerialName("date_launched")
    val dateLaunched: String,
    val notice: String? = null,
    val countries: List<String> = emptyList(),
    val fiats: List<String>,
    val tags: String? = null,
    val type: String,
    @SerialName("maker_fee")
    val makerFee: Double,
    @SerialName("taker_fee")
    val takerFee: Double,
    @SerialName("weekly_visits")
    val weeklyVisits: Long,
    @SerialName("spot_volume_usd")
    val spotVolumeUsd: Double,
    @SerialName("spot_volume_last_updated")
    val spotVolumeLastUpdated: String,
    val urls: Urls
)

@Serializable
data class Urls (
    val website: List<String>,
    val twitter: List<String>,
    val blog: List<String>,
    val chat: List<String>,
    val fee: List<String>
)
