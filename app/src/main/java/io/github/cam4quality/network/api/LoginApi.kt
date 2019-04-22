package io.github.cam4quality.network.api

import io.github.cam4quality.network.entity.request.LoginRequestModel
import io.github.cam4quality.network.entity.response.LoginResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun login(@Body loginRequestModel: LoginRequestModel): Single<Response<LoginResponseModel>>
}