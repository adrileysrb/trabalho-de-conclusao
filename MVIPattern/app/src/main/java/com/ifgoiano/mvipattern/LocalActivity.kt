package com.ifgoiano.mvipattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifgoiano.mvipattern.databinding.ActivityLocalBinding
import com.ifgoiano.mvipattern.local.DatabaseHandler
import com.ifgoiano.mvipattern.local.ListaAdapter
import com.ifgoiano.mvipattern.model.Data

class LocalActivity : AppCompatActivity() { // Iniciando a RecyclerView
var listaAdapter: ListaAdapter? = null
var linearLayoutManager: LinearLayoutManager? = null

// SQLite
var pessoaList = ArrayList<Data>()
var databaseHandler = DatabaseHandler(this)

private lateinit var binding: ActivityLocalBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLocalBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    setTitle("MVI Activity - LOCAL")
    //initView()

    binding.refreshData.setOnClickListener {
        refreshData()
    }

    binding.deleteData.setOnClickListener {
        deleteDialog()
    }

    binding.insertData.setOnClickListener {
        inputDialog()
    }

    binding.updateData.setOnClickListener {
        updateDialog()
    }
}

    override fun onResume() {
        super.onResume()
        //initView()
    }

    private fun initView(){
        pessoaList = databaseHandler.pessoas()
        listaAdapter = ListaAdapter(pessoaList,this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)
        binding.listSql.layoutManager = linearLayoutManager
        binding.listSql.adapter = listaAdapter
    }
    private fun deleteAdapter(position: Int){
        pessoaList.removeAt(position)
        listaAdapter!!.notifyItemRemoved(position)
    }

    fun deleteDialog(){
        var al = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.delete_dialog, null)
        al.setView(view)
        val id_input = view.findViewById<EditText>(R.id.id_input)
        var delete_btn = view.findViewById<Button>(R.id.delete_btn)
        val alertDialog =  al.show()

        delete_btn.setOnClickListener { v ->
            run {
                deleteAdapter(id_input.text.toString().toInt())
                alertDialog.dismiss()
                refreshData()
            }
        }
    }

    fun inputDialog(){
        var al = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.insert_dialog, null)
        al.setView(view)
        val name = view.findViewById<EditText>(R.id.name)
        var insert_btn = view.findViewById<Button>(R.id.insert_btn)
        val alertDialog =  al.show()

        insert_btn.setOnClickListener { v ->
            run {
                var data = Data()
                data.name = name.text.toString()
                databaseHandler.addPessoa(data)
                alertDialog.dismiss()
                refreshData()
            }
        }
    }

    fun updateDialog(){
        var al = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.insert_dialog, null)
        al.setView(view)
        val name = view.findViewById<EditText>(R.id.name)
        var insert_btn = view.findViewById<Button>(R.id.insert_btn)
        val alertDialog =  al.show()

        insert_btn.setOnClickListener { v ->
            run {
                var data = Data()
                data.name = name.text.toString()
                databaseHandler.addPessoa(data)
                alertDialog.dismiss()
                refreshData()
            }
        }
    }

    fun refreshData(){
        binding.dataListCount.text = "ALL DATA COUNT : " + databaseHandler.pessoas().size
        initView()
    }
}