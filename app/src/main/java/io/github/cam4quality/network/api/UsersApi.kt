package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.request.UserRegisterRequestModel
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UsersApi {
    @GET("/api/User/allUsers")
    fun getAllUsers(@Header(NetworkConstants.HEADER_AUTHORIZATION) token: String): Single<Response<List<UserResponseModel>>>

    @POST("/api/User/register")
    fun register(@Body userRegisterModel: UserRegisterRequestModel): Single<ResponseBody>

    @HTTP(method = "DELETE", path = "/api/User/removeUser", hasBody = true)
    fun removeUser(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body removeModel: RemoveRequestModel
    ): Single<ResponseBody>
}