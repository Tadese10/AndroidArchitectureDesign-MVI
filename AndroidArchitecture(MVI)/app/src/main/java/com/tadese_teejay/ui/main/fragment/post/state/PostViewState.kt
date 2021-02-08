package com.tadese_teejay.ui.main.fragment.post.state

import com.tadese_teejay.model.main.Post
import com.tadese_teejay.model.main.PostComment

data class PostViewState constructor(
    var post : ArrayList<Post> = ArrayList()
){
}