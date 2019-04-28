package io.github.cam4quality.network.entity.response

data class FactoryAllTimeStatisticsResponseModel(
    val allDetailsCount: Int,
    val factoryName: String,
    val failedDetailsCount: Double,
    val failedPercent: Double
)