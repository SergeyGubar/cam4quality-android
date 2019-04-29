package io.github.cam4quality.network.entity.response

data class FactoryFailDailyStatisticsResponseModel(
    val date: String,
    val failPercent: Double
)