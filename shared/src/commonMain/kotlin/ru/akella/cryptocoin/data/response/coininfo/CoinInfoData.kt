package ru.akella.cryptocoin.data.response.coininfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinInfoData(
    val urls: Urls,
    val logo: String,
    val id: Long,
    val name: String,
    val symbol: String,
    val slug: String,
    val description: String,
    @SerialName("date_added")
    val dateAdded: String,
    @SerialName("date_launched")
    val dateLaunched: String,
    val tags: List<String>,
    val platform: String? = null,
    val category: String,
    val notice: String? = null,
    @SerialName("self_reported_circulating_supply")
    val selfReportedCirculatingSupply: String? = null,
    @SerialName("self_reported_market_cap")
    val selfReportedMarketCap: String? = null,
    @SerialName("self_reported_tags")
    val selfReportedTags: String? = null,
    @SerialName("infinite_supply")
    val infiniteSupply: Boolean? = null
)

@Serializable
data class Urls (
    val website: List<String>,
    @SerialName("technical_doc")
    val technicalDoc: List<String>,
    val twitter: List<String>,
    val reddit: List<String>,
    @SerialName("message_board")
    val messageBoard: List<String>,
    val announcement: List<String>,
    val chat: List<String>,
    val explorer: List<String>,
    @SerialName("source_code")
    val sourceCode: List<String>
)
