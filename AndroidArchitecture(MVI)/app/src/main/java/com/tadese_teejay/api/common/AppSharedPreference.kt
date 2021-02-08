package com.tadese_teejay.api.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tadese_teejay.model.auth.UserData
import com.tadese_teejay.utils.Constants
import com.tadese_teejay.utils.Constants.sharedPrefFile
import kotlin.reflect.typeOf

class AppSharedPreference constructor(val context: Context){

    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    fun saveUserData(userData: UserData) {
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(Constants.Key_User, Gson().toJson(userData))
        editor.apply()
        editor.commit()
    }

    fun getUserData(): UserData?{
        return Gson().fromJson(sharedPreferences.getString(Constants.Key_User, ""),
            UserData::class.java)
    }

}