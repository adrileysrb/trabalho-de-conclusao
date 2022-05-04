package com.ifgoiano.mvipattern.local

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifgoiano.mvipattern.LocalActivity
import com.ifgoiano.mvipattern.R
import com.ifgoiano.mvipattern.model.Data

class ListaAdapter (nameList: List<Data>, internal var ctx: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListaAdapter.ViewHolder>() {

    internal var nameList: List<Data> = ArrayList<Data>()
    init {
        this.nameList = nameList
    }

    // Aqui é onde o viewholder é criado a partir do layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    // Nessa parte é onde se modifica o item do viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = nameList[position]

        holder.name.text = name.name
        if(position % 2 == 0) holder.name.setBackgroundColor(Color.GRAY)
        else holder.name.setBackgroundColor(Color.WHITE)
        holder.name.setOnClickListener {
            val intent = Intent(ctx, LocalActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("position", name.id)
            ctx.startActivity(intent)
        }
       /* holder.btn.setOnClickListener {
            val databaseHandler = DatabaseHandler(ctx)
            name.id?.let { it1 -> databaseHandler.deletePessoa(it1.toInt()) }
            callbacks(position)
        }*/
    }

    // Devolve quantidade de itens do nameList
    override fun getItemCount(): Int {
        return nameList.size
    }

    // Aqui é a criação dos itens do viewholder
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.blog_title)
    }
}