package com.tadese_teejay.model.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostComment constructor(
    @Expose
    @SerializedName("postId")
    val postId : Int,

    @Expose
    @SerializedName("id")
    val id : Int,

    @Expose
    @SerializedName("name")
    val name : String,

    @Expose
    @SerializedName("email")
    val email : String,

    @Expose
    @SerializedName("body")
    val body : String,
) {
    override fun toString(): String {
        return "PostComment(postId=$postId, id=$id, name='$name', email='$email', body='$body')"
    }
}