package io.github.cam4quality.network.entity.request

data class UserReisterRequestModel(
    val email: String,
    val userName: String,
    val factoryId: String,
    val password: String
)