package com.tadese_teejay.repository.main

import androidx.lifecycle.LiveData
import com.tadese_teejay.api.common.*
import com.tadese_teejay.api.main.post.PostApiService
import com.tadese_teejay.api.main.todo.TodoApiService
import com.tadese_teejay.model.main.Post
import com.tadese_teejay.model.main.PostComment
import com.tadese_teejay.model.main.Todo
import com.tadese_teejay.ui.main.fragment.post.state.CommentViewState
import com.tadese_teejay.ui.main.fragment.post.state.PostViewState
import com.tadese_teejay.ui.main.fragment.todo.state.TodoViewState

class PostRepository {

    fun getPosts(): LiveData<DataState<PostViewState>> {
        return object : NetworkBoundResource<ArrayList<Post>, PostViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ArrayList<Post>>) {
                result.value = DataState.data(
                    data = PostViewState(
                        post = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<ArrayList<Post>>> {
                return AppRetrofitBuilder.retrofitBuilder.build().create(PostApiService::class.java).getPosts()
            }

        }.asLiveData()
    }

    fun getPostComments(postId: Int): LiveData<DataState<CommentViewState>> {
        return object : NetworkBoundResource<ArrayList<PostComment>, CommentViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ArrayList<PostComment>>) {
                result.value = DataState.data(
                    data = CommentViewState(
                        postComment = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<ArrayList<PostComment>>> {
                return AppRetrofitBuilder.retrofitBuilder.build().create(PostApiService::class.java).getPostComment(postId)
            }

        }.asLiveData()
    }

}