package com.tadese_teejay.ui.main.fragment.post

import android.util.Log
import android.util.Log.DEBUG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tadese_teejay.R
import com.tadese_teejay.model.main.Post
import com.tadese_teejay.repository.main.PostRepository
import com.tadese_teejay.ui.main.fragment.post.state.CommentViewEvent
import com.tadese_teejay.ui.main.fragment.post.state.PostViewEvent
import kotlinx.android.synthetic.main.post_item_layout.view.*
import kotlinx.android.synthetic.main.todo_fragment_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostRecyclerAdapter(
    private val interaction: Interaction? = null,
    val viewModel: com.tadese_teejay.ui.main.fragment.post.PostViewModel,
    val fragment: PostFragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PostViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_item_layout,
                parent,
                false
            ),
            interaction,
            viewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewModel -> {
                holder.bind(differ.currentList.get(position), position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Post>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    class PostViewModel
    constructor(
        itemView: View,
        private val interaction: Interaction?,val viewModel : com.tadese_teejay.ui.main.fragment.post.PostViewModel
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Post, position: Int) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = "${item.id}. ${item.title}"
            itemView.postBody.text = item.body
            itemView.comment.text = "Fetching comment..."
            itemView.userId.text = item.userId.toString()

            if(item.comments.isNullOrEmpty()){
                item.comments = ArrayList()
                var mData = PostRepository().getPostComments(item.id)
                mData.observe(itemView.context as LifecycleOwner){
                    it.data?.let {
                        item.comments = it!!.postComment
                        mData.removeObservers(itemView.context as LifecycleOwner)
                        viewModel.setSinglePost(item, adapterPosition)
                    }
                }

            }else{
                itemView.comment.text = "${item.comments?.size.toString()} comment${if (item.comments?.size!! > 0) "s" else ""}"
            }

        }

    }
    interface Interaction {
        fun onItemSelected(position: Int, item: Post)
    }
}