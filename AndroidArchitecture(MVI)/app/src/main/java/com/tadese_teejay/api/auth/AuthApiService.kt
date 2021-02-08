package com.tadese_teejay.api.auth

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.common.GenericApiResponse
import com.tadese_teejay.model.auth.UserData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {

    @GET("users?")
    fun getUserDetails(@Query("username") username : String): LiveData<GenericApiResponse<List<UserData>>>
}