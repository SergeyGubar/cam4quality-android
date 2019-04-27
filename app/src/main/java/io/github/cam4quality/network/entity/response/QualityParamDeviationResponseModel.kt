package io.github.cam4quality.network.entity.response

data class QualityParamDeviationResponseModel(
    val description: String,
    val id: String,
    val maxValueDeviation: Double,
    val minValueDeviation: Double,
    val normalValue: Double
)