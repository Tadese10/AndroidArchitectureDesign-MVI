package com.tadese_teejay.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tadese_teejay.api.common.AbsentLiveData
import com.tadese_teejay.api.common.DataState
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.repository.auth.AuthRepository
import com.tadese_teejay.ui.auth.state.AuthViewState
import com.tadese_teejay.ui.auth.state.AuthViewStateEvent

class AuthViewModel : ViewModel(){

    private val _stateEvent: MutableLiveData<AuthViewStateEvent> = MutableLiveData()
    private val _viewState : MutableLiveData<AuthViewState> = MutableLiveData()

    val viewState : LiveData<AuthViewState>
    get() = _viewState

    val data : LiveData<DataState<AuthViewState>> = Transformations.switchMap(_stateEvent){event ->
        event?.let {
            handleStateEvent(event)
        }
    }

    private fun handleStateEvent(event: AuthViewStateEvent): LiveData<DataState<AuthViewState>> {
        return when(event){
            is AuthViewStateEvent.GetUserEvent -> {
                return AuthRepository().getUserDetails(event.username)
            }

            is AuthViewStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setUser(user: UserData){
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew():AuthViewState{
        return viewState.value?.let {
            it
        } ?: AuthViewState()
    }

    fun setStateEvent(event : AuthViewStateEvent){
        _stateEvent.value = event
    }
}