package com.tadese_teejay.ui.main.fragment.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tadese_teejay.R
import com.tadese_teejay.model.main.Post
import com.tadese_teejay.ui.main.fragment.post.state.PostViewEvent
import com.tadese_teejay.ui.main.fragment.todo.TodoRecyclerAdapter
import com.tadese_teejay.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.post_fragment_layout.*
import kotlinx.android.synthetic.main.todo_fragment_layout.swipeContainer

class PostFragment: Fragment(), PostRecyclerAdapter.Interaction{
    lateinit var viewModel: PostViewModel
    lateinit var postListAdapter : PostRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)
         ?: throw Exception("Invalid Activity")

        Observe()
        initRecyclerView()
        FetchPostTodo();
        swipeContainer.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                FetchPostTodo()
            }
        })
    }

    private fun FetchPostTodo() {
        viewModel.setEvent(PostViewEvent.GetPosts())
    }

    private fun Observe() {
        //Observe event changes
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            swipeContainer.isRefreshing = dataState.loading

            dataState.data?.let { todoViewState ->
                todoViewState.post?.let { post ->
                    if(post.isEmpty())
                        dataState.message = "No Post found!"
                    viewModel.setPosts(post)
                }
            }

            //Handle message
            dataState.message?.let { message ->
                showToast(message)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {

            it.post?.let {
                UpdateAdapterList(it)
            }
        })
    }

    private fun UpdateAdapterList(data: List<Post>, position: Int = 0) {
        postListAdapter.submitList(data)

    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        post_recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacing = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacing)
            postListAdapter = PostRecyclerAdapter(this@PostFragment,viewModel, this@PostFragment)
            adapter = postListAdapter
        }
    }
    override fun onDetach() {
        super.onDetach()
        RemoveAllObservers()
    }

    private fun RemoveAllObservers() {
        viewModel.dataState.removeObservers(this)
    }

    override fun onItemSelected(position: Int, item: Post) {

    }

}