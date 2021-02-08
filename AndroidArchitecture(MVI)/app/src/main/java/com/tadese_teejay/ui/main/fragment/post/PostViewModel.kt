package com.tadese_teejay.ui.main.fragment.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tadese_teejay.api.common.AbsentLiveData
import com.tadese_teejay.api.common.DataState
import com.tadese_teejay.model.main.Post
import com.tadese_teejay.model.main.PostComment
import com.tadese_teejay.repository.main.PostRepository
import com.tadese_teejay.ui.main.fragment.post.state.PostViewEvent
import com.tadese_teejay.ui.main.fragment.post.state.PostViewState
import com.tadese_teejay.ui.main.fragment.todo.state.TodoViewState

class PostViewModel : ViewModel() {

    private val _viewState : MutableLiveData<PostViewState> = MutableLiveData()
    private val _postEvent : MutableLiveData<PostViewEvent> = MutableLiveData()

    val viewState : LiveData<PostViewState>
    get() = _viewState

    val dataState : LiveData<DataState<PostViewState>> = Transformations.switchMap(_postEvent){event ->
        event?.let { handleEvent(event)  }
    }

    private fun handleEvent(post_event: PostViewEvent): LiveData<DataState<PostViewState>> {
       return  when(post_event){
           is PostViewEvent.GetPosts ->{
               return PostRepository().getPosts()
           }

           is PostViewEvent.None ->{
               AbsentLiveData.create()
           }
       }
    }

    fun setEvent(event : PostViewEvent){
        _postEvent.value = event
    }

    fun setPosts(data: ArrayList<Post>){
        val update = getCurrentViewStateOrNew()
        update.post = data
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): PostViewState {
        return viewState.value?.let {
            it
        } ?: PostViewState()
    }

    fun setSinglePost(item: Post, position: Int) {
        val update = getCurrentViewStateOrNew()
        var itemfind = update.post.find { it.id == item.id }
        itemfind?.comments = item.comments
        update.post[update.post.indexOf(itemfind)] = item
        _viewState.value = update
    }
}