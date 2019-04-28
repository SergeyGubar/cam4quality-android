package io.github.cam4quality.network.entity.response

data class AllFactoriesStatisticsResponseModel(
    val detailsCount: Int,
    val factoryId: String,
    val factoryName: String,
    val failurePercent: Double,
    val successPercent: Double
)