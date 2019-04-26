package io.github.cam4quality.network.entity.response

data class UserResponseModel(
    val id: String,
    val userName: String,
    val email: String,
    val factory: FactoryResponseModel?
)