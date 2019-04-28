package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.FactoryAddRequestModel
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.FactoryResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface FactoriesApi {
    @GET("/api/allFactories")
    fun getAllFactories(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String
    ): Single<Response<List<FactoryResponseModel>>>

    @POST("/api/addFactory")
    fun addFactory(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body factoryRequestModel: FactoryAddRequestModel
    ): Single<ResponseBody>

    @HTTP(method = "DELETE", path = "/api/removeFactory", hasBody = true)
    fun removeFactory(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body removeModel: RemoveRequestModel
    ): Single<ResponseBody>
}