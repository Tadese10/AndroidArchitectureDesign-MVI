package com.tadese_teejay.repository.auth

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.auth.AuthApiService
import com.tadese_teejay.api.common.*
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.ui.auth.state.AuthViewState

class AuthRepository {

    fun getUserDetails(username: String): LiveData<DataState<AuthViewState>> {
        return object : NetworkBoundResource<List<UserData>, AuthViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<UserData>>) {
                result.value = DataState.data(
                    data = AuthViewState(
                        user = if(response.body.isNotEmpty()) response.body[0] else null,
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<UserData>>> {
                return AppRetrofitBuilder.retrofitBuilder.build().create(AuthApiService::class.java).getUserDetails(username)
            }

        }.asLiveData()
    }

}