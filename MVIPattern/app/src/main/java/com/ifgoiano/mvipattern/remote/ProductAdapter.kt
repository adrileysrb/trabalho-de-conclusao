package com.ifgoiano.mvipattern.remote

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ifgoiano.mvipattern.R
import com.ifgoiano.mvipattern.model.Data

class ProductAdapter(
    var list: List<Data>,
    var onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ProductViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bindItem(item)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item, position)
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(item: Data, position: Int)
        fun onDelete(item: Data, position: Int)
    }
}