package io.github.cam4quality.network.entity.response

import io.github.cam4quality.contract.IdentifiableObject

data class DetailResponseModel(
    val dateTime: String,
    val factory: FactoryResponseModel,
    override val id: String,
    val isCritical: Boolean,
    val params: List<QualityParamResponseModel>,
    val photo: PhotoResponseModel
): IdentifiableObject