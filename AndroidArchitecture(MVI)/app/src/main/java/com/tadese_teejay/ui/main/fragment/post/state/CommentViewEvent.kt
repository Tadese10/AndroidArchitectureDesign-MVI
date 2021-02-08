package com.tadese_teejay.ui.main.fragment.post.state

sealed class CommentViewEvent {
    class GetPostComments(val postId: Int) : CommentViewEvent()
    class None() : CommentViewEvent()
}