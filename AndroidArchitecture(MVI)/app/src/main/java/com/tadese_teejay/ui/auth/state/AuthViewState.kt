package com.tadese_teejay.ui.auth.state

import com.tadese_teejay.model.auth.UserData

data class AuthViewState(
    var user: UserData? = null
)