package io.github.cam4quality.network.entity.response

data class FactoryStatisticInIntervalResponseModel(
    val allDetailsCount: Int,
    val factoryName: String,
    val failedDetailsCount: Int,
    val failedPercent: String
)