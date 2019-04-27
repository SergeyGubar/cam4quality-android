package io.github.cam4quality.network.entity.request

data class QualityParamDeviationRequestModel(
    val description: String,
    val maxValue: Double,
    val minValue: Double,
    val normalValue: Double
)