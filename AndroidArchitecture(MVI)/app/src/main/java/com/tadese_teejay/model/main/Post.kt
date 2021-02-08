package com.tadese_teejay.model.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Post(
    @Expose
    @SerializedName("userId")
    val userId: Int,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("body")
    val body: String,

    @Expose
    @SerializedName("title")
    val title: String,
    var comments: List<PostComment>?
){
    override fun toString(): String {
        return "Post(userId=$userId, id=$id, body='$body', title=$title)"
    }
}
