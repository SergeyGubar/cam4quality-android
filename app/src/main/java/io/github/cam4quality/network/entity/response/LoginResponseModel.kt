package io.github.cam4quality.network.entity.response

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(@SerializedName("access_token") val token: String,
                              @SerializedName("username") val userName: String)