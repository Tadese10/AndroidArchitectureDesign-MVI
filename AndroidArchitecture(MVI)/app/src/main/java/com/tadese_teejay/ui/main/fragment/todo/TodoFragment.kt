package com.tadese_teejay.ui.main.fragment.todo

import android.content.Context
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
import com.tadese_teejay.api.common.AppSharedPreference
import com.tadese_teejay.model.main.Todo
import com.tadese_teejay.ui.main.fragment.todo.state.TodoStateEvent
import com.tadese_teejay.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.todo_fragment_layout.*

class TodoFragment: Fragment(), TodoRecyclerAdapter.Interaction {

    lateinit var viewModel: TodoViewModel
    lateinit var todoListAdapter : TodoRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.todo_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(TodoViewModel::class.java)
         ?: throw Exception("Invalid Activity")

        Observe()
        initRecyclerView()
        FetchUserTodo();
        swipeContainer.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
               FetchUserTodo()
            }
        })
    }

    private fun FetchUserTodo() {
        viewModel.setStateEvent(TodoStateEvent.GetUserTodo(AppSharedPreference(requireActivity()
            .applicationContext).getUserData()?.id.toString()))
    }

    private fun initRecyclerView(){
        todo_recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacing = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacing)
            todoListAdapter = TodoRecyclerAdapter(this@TodoFragment)
            adapter = todoListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        RemoveAllObservers()
    }

    private fun RemoveAllObservers() {
        viewModel.dataState.removeObservers(this)
    }

    private fun Observe() {
        //Observe event changes
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            //Handle Loading
            //showProgressBar(dataState.loading)
            swipeContainer.isRefreshing = dataState.loading

            dataState.data?.let { todoViewState ->
                todoViewState.todo?.let { todos ->
                    if(todos.isEmpty())
                        dataState.message = "No Todo found!"
                    viewModel.setTodoList(todos)
                }
            }

            //Handle message
            dataState.message?.let { message ->
                showToast(message)
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {

            it.todo?.let {
                UpdateAdapterList(it)
            }
        })

    }

    private fun UpdateAdapterList(todos: List<Todo>) {
        todoListAdapter.submitList(todos)
    }

    private fun showProgressBar(loading: Boolean) {
        loadingBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(position: Int, item: Todo) {

    }
}