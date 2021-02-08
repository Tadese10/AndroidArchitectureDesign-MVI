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
import com.tadese_teejay.ui.main.fragment.post.state.CommentViewEvent
import com.tadese_teejay.ui.main.fragment.post.state.CommentViewState
import com.tadese_teejay.ui.main.fragment.post.state.PostViewEvent
import com.tadese_teejay.ui.main.fragment.post.state.PostViewState

class CommentViewModel :ViewModel() {
    private val _viewState : MutableLiveData<CommentViewState> = MutableLiveData()
    private val _commentEvent : MutableLiveData<CommentViewEvent> = MutableLiveData()

    val viewState : LiveData<CommentViewState>
        get() = _viewState

    val dataState : LiveData<DataState<CommentViewState>> = Transformations.switchMap(_commentEvent){ event ->
        event?.let { handleEvent(event)  }
    }

    private fun handleEvent(event: CommentViewEvent): LiveData<DataState<CommentViewState>> {
        return  when(event){
            is CommentViewEvent.GetPostComments ->{
                return PostRepository().getPostComments(event.postId)
            }

            is CommentViewEvent.None ->{
                AbsentLiveData.create()
            }
        }
    }

    fun getCurrentViewStateOrNew(): CommentViewState {
        return viewState.value?.let {
            it
        } ?: CommentViewState()
    }

    fun setComment(it: List<PostComment>) {
        val update = getCurrentViewStateOrNew()
        update.postComment = it
        _viewState.value = update
    }

    fun setCommentEvent(event: CommentViewEvent) {
        _commentEvent.value = event
    }


}