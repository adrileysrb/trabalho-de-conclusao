package com.ifgoiano.mvipattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifgoiano.mvipattern.databinding.ActivityHomeBinding
import com.ifgoiano.mvipattern.databinding.ActivityRemoteBinding
import com.ifgoiano.mvipattern.model.Data
import com.ifgoiano.mvipattern.remote.ProductAdapter
import com.ifgoiano.mvipattern.remote.ProductViewModel

class RemoteActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    private lateinit var name: EditText

    private lateinit var productAdapter: ProductAdapter
    private lateinit var list: ArrayList<Data>

    private var selected: Data = Data()

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var binding: ActivityRemoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle("MVI Activity - REMOTE")

        binding.refreshData2.setOnClickListener {
            refreshData()
        }

        binding.insertData2.setOnClickListener {
            inputDialog()
        }

        binding.deleteData2.setOnClickListener {
            deleteDialog()
        }

        binding.updateData2.setOnClickListener {
            updateDialog()
        }

    }

    private fun initElement() {

        productViewModel.getList()

    }

    private fun initViewModel() {
        productViewModel.createLiveData.observe(this, {
            onCreate(it)
        })

        productViewModel.updateLiveData.observe(this, {
            onUpdate(it)
        })

        productViewModel.deleteLiveData.observe(this, {
            onDelete(it)
        })

        productViewModel.getListLiveData.observe(this, {
            onGetList(it)
        })
    }

    private fun onCreate(it: Boolean) {
        if (it) {
            productViewModel.getList()
           // resetText()
        }
    }

    private fun onUpdate(it: Boolean) {
        if (it) {
            productViewModel.getList()
            //resetText()
        }
    }

    private fun onDelete(it: Boolean) {
        if (it) {
            productViewModel.getList()
            //resetText()
        }
    }

    private fun onGetList(it: List<Data>) {
        list = ArrayList()
        list.addAll(it)

        productAdapter = ProductAdapter(list, this)

        binding.listFirebase.adapter = productAdapter
        binding.listFirebase.layoutManager = LinearLayoutManager(baseContext)

        productAdapter.notifyDataSetChanged()
    }

    override fun onClick(item: Data, position: Int) {
        selected = item
        name.setText(selected.name)
    }

    override fun onDelete(item: Data, position: Int) {
        productViewModel.delete(item.id!!)
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
                productViewModel.delete(id_input.text.toString())
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
                productViewModel.create(data)
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
                productViewModel.create(data)
                alertDialog.dismiss()
                refreshData()
            }
        }
    }

    fun refreshData(){
        initElement()
        initViewModel()
    }
}