package io.github.cam4quality.network.entity.response

import io.github.cam4quality.contract.IdentifiableObject

data class QualityParamResponseModel(
    override val id: String,
    val name: String,
    val value: Double,
    val paramDeviation: QualityParamDeviationResponseModel
): IdentifiableObject