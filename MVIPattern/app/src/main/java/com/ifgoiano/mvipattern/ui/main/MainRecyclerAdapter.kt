package com.ifgoiano.mvipattern.ui.main


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ifgoiano.mvipattern.R
import com.ifgoiano.mvipattern.model.Data

class MainRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogPostViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("ADRILEY", differ.currentList.size.toString());
        return differ.currentList.size
    }

    fun submitList(list: List<Data>) {
        differ.submitList(list)
    }

    class BlogPostViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Data) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.findViewById<TextView>(R.id.blog_title).text = item.name
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Data)
    }
}


