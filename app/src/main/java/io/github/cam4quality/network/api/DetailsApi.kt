package io.github.cam4quality.network.api

import io.github.cam4quality.network.constant.NetworkConstants
import io.github.cam4quality.network.entity.request.RemoveRequestModel
import io.github.cam4quality.network.entity.response.DetailResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header

interface DetailsApi {
    @GET("/api/allDetails")
    fun getAllDetails(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String
    ): Single<Response<List<DetailResponseModel>>>

    @HTTP(method = "DELETE", path = "/api/removeDetail", hasBody = true)
    fun removeDetail(
        @Header(NetworkConstants.HEADER_AUTHORIZATION) token: String,
        @Body removeModel: RemoveRequestModel
    ): Single<ResponseBody>
}