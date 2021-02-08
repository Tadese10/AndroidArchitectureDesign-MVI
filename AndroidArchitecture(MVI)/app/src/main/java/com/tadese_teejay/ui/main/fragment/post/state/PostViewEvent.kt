package com.tadese_teejay.ui.main.fragment.post.state

sealed class PostViewEvent{
    class GetPosts(): PostViewEvent()
    class None() : PostViewEvent()
}