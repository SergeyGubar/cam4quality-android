package io.github.cam4quality.network.entity.response

import io.github.cam4quality.contract.IdentifiableObject

data class FactoryResponseModel(
    val address: String,
    override val id: String,
    val name: String,
    val type: String
) : IdentifiableObject