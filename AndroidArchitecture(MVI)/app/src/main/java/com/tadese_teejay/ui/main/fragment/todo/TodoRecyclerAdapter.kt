package com.tadese_teejay.ui.main.fragment.todo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tadese_teejay.R
import com.tadese_teejay.model.main.Todo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Todo>() {

        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.todo_item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Todo>) {
        differ.submitList(list)
    }

    class TodoViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Todo) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = "Todo: ${item.id}"
            itemView.statusText.text = if(item.completed) "Completed" else "Uncompleted"
            itemView.statusText.setTextColor(if(item.completed) Color.BLACK else Color.RED)
            itemView.todoBody.text = item.title
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Todo)
    }
}