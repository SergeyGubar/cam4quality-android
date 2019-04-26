package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UsersApi {
    @GET("/api/User/allUsers")
    fun getAllUsers(@Header(NetworkConstants.HEADER_AUTHORIZATION) token: String): Single<Response<List<UserResponseModel>>>
}