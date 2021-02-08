package com.tadese_teejay.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val Name: String,
    @Expose
    @SerializedName("username")
    val Username: String,
    @Expose
    @SerializedName("email")
    val Email: String
){
    override fun toString(): String {
        return "UserData(id=$id, Name='$Name', Username='$Username', Email='$Email')"
    }
}