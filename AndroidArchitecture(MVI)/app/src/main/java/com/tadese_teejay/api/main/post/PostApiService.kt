package com.tadese_teejay.api.main.post

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.common.GenericApiResponse
import com.tadese_teejay.model.main.Post
import com.tadese_teejay.model.main.PostComment
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService {

    @GET("posts")
    fun getPosts(): LiveData<GenericApiResponse<ArrayList<Post>>>

    @GET("comments?")
    fun getPostComment(@Query("postId") postId : Int): LiveData<GenericApiResponse<ArrayList<PostComment>>>

}