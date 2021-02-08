package com.tadese_teejay.ui.main.fragment.todo.state

import com.tadese_teejay.ui.auth.state.AuthViewStateEvent

sealed class TodoStateEvent {
    class GetUserTodo( val userId: String): TodoStateEvent()
    class None: TodoStateEvent()
}