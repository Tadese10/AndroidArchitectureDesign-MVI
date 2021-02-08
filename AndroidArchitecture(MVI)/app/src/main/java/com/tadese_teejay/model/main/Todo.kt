package com.tadese_teejay.model.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Todo (
    @Expose
    @SerializedName("id")
    val id : Int,

    @Expose
    @SerializedName("title")
    val title : String,

    @Expose
    @SerializedName("completed")
    val completed : Boolean,

){
    override fun toString(): String {
        return "Todo(id=$id, title=$title, completed=$completed)"
    }
}