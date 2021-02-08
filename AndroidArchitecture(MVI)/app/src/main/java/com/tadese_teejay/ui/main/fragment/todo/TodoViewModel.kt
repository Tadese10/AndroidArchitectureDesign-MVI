package com.tadese_teejay.ui.main.fragment.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tadese_teejay.api.common.AbsentLiveData
import com.tadese_teejay.api.common.DataState
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.model.main.Todo
import com.tadese_teejay.repository.auth.AuthRepository
import com.tadese_teejay.repository.main.TodoRepository
import com.tadese_teejay.ui.auth.state.AuthViewState
import com.tadese_teejay.ui.auth.state.AuthViewStateEvent
import com.tadese_teejay.ui.main.fragment.todo.state.TodoStateEvent
import com.tadese_teejay.ui.main.fragment.todo.state.TodoViewState

class TodoViewModel: ViewModel() {

   private val _stateEvent : MutableLiveData<TodoStateEvent> = MutableLiveData()
   private val _viewState : MutableLiveData<TodoViewState> = MutableLiveData()

    val viewState : LiveData<TodoViewState>
        get() = _viewState

    val dataState: LiveData<DataState<TodoViewState>> = Transformations.switchMap(_stateEvent){  _stateEvent ->
        _stateEvent?.let {
            handleEvent(_stateEvent)
        }
    }

    private fun handleEvent(_stateEvent: TodoStateEvent?): LiveData<DataState<TodoViewState>> {
        return when(_stateEvent){

            is TodoStateEvent.GetUserTodo -> {
                TodoRepository().getUserTodo(_stateEvent.userId)
            }

            is TodoStateEvent.None -> {
                AbsentLiveData.create()
            }
            null ->  AbsentLiveData.create()
        }
    }

    fun setTodoList(todos: List<Todo>){
        val update = getCurrentViewStateOrNew()
        update.todo = todos
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew():TodoViewState{
        return viewState.value?.let {
            it
        } ?: TodoViewState()
    }

    fun setStateEvent(event : TodoStateEvent){
        _stateEvent.value = event
    }

}