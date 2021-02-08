package com.tadese_teejay.ui.main.fragment.post.state

import com.tadese_teejay.model.main.Post
import com.tadese_teejay.model.main.PostComment

data class CommentViewState constructor(
    var postComment : List<PostComment> = ArrayList()
){
    override fun toString(): String {
        return "CommentViewState(postComment=$postComment)"
    }
}