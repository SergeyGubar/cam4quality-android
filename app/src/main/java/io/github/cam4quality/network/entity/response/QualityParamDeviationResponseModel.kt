package io.github.cam4quality.network.entity.response

import io.github.cam4quality.contract.IdentifiableObject

data class QualityParamDeviationResponseModel(
    val description: String,
    override val id: String,
    val maxValueDeviation: Double,
    val minValueDeviation: Double,
    val normalValue: Double
) : IdentifiableObject