package com.ifgoiano.mvipattern.remote

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifgoiano.mvipattern.R
import com.ifgoiano.mvipattern.model.Data

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val nameText: TextView = itemView.findViewById(R.id.blog_title)

    fun bindItem(product: Data) {
        itemView.apply {
            nameText.text = product.name
        }
    }
}