package com.tadese_teejay.repository.main

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.auth.AuthApiService
import com.tadese_teejay.api.common.*
import com.tadese_teejay.api.main.todo.TodoApiService
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.model.main.Todo
import com.tadese_teejay.ui.auth.state.AuthViewState
import com.tadese_teejay.ui.main.fragment.todo.state.TodoViewState

class TodoRepository{
    fun getUserTodo(userId: String): LiveData<DataState<TodoViewState>> {
        return object : NetworkBoundResource<List<Todo>, TodoViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Todo>>) {
                result.value = DataState.data(
                    data = TodoViewState(
                        todo = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Todo>>> {
                return AppRetrofitBuilder.retrofitBuilder.build().create(TodoApiService::class.java).getUserTodo(userId)
            }

        }.asLiveData()
    }
}