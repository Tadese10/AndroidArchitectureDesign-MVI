package com.tadese_teejay.api.main.todo

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.common.GenericApiResponse
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.model.main.Todo
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApiService {

    @GET("todos?")
    fun getUserTodo(@Query("userId") userId : String): LiveData<GenericApiResponse<List<Todo>>>
}