package com.tadese_teejay.ui.main.fragment.todo.state

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.model.main.Todo

data class TodoViewState(
    var todo: List<Todo>? = null
)