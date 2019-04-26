package io.github.cam4quality.network.repository

import io.github.cam4quality.network.api.UsersApi
import io.github.cam4quality.network.entity.response.UserResponseModel
import io.github.cam4quality.utility.extension.mapToResult
import io.github.cam4quality.utility.extension.workOnBackground
import io.github.cam4quality.utility.helper.SharedPrefHelper
import io.reactivex.Single

class UsersRepository(
    private val api: UsersApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllUsers(): Single<Result<List<UserResponseModel>>> {
        return api.getAllUsers(prefHelper.getBearerToken())
            .mapToResult()
            .workOnBackground()
    }
}