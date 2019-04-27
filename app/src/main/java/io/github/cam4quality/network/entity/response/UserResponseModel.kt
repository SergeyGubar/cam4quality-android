package io.github.cam4quality.network.entity.response

import io.github.cam4quality.contract.IdentifiableObject

data class UserResponseModel(
    override val id: String,
    val userName: String,
    val email: String,
    val factory: FactoryResponseModel?
): IdentifiableObject