package io.github.cam4quality.network.entity.request

data class QualityParamRequestModel(
    val name: String,
    val qualityParamDeviationId: String,
    val standardValue: Double
)