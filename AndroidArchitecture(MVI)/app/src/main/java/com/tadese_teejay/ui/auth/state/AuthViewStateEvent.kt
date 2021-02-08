package com.tadese_teejay.ui.auth.state

sealed class AuthViewStateEvent {
    class GetUserEvent( val username: String): AuthViewStateEvent()
    class None: AuthViewStateEvent()
}