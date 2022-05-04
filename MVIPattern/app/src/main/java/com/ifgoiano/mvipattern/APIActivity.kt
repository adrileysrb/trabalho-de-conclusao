package com.ifgoiano.mvipattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifgoiano.mvipattern.databinding.ActivityApiactivityBinding
import com.ifgoiano.mvipattern.model.Data
import com.ifgoiano.mvipattern.ui.DataStateListener
import com.ifgoiano.mvipattern.ui.main.MainRecyclerAdapter
import com.ifgoiano.mvipattern.ui.main.MainViewModel
import com.ifgoiano.mvipattern.ui.main.state.MainStateEvent
import com.ifgoiano.mvipattern.util.DataState
import com.ifgoiano.mvipattern.util.TopSpacingItemDecoration

class APIActivity : AppCompatActivity(), DataStateListener, MainRecyclerAdapter.Interaction {

    private lateinit var binding: ActivityApiactivityBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener
    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    private val TAG: String = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TIMESYSTEM", System.currentTimeMillis().toString())
        super.onCreate(savedInstanceState)
        binding = ActivityApiactivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle("MVI Activity - API")

        try{
            dataStateHandler = this as DataStateListener
        }catch(e: ClassCastException){
            println("$this must implement DataStateListener")
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
        triggerGetCountriesEvent()
        //triggerGetFirebaseListEvent()

    }

    private fun initRecyclerView(){
        binding.list.apply {
            layoutManager = LinearLayoutManager(this@APIActivity.baseContext)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = MainRecyclerAdapter(this@APIActivity)
            adapter = mainRecyclerAdapter
        }
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->

            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let{ event ->
                event.getContentIfNotHandled()?.let{ mainViewState ->

                    println("DEBUG: DataState: ${mainViewState}")

                    mainViewState.dataList?.let{
                        viewModel.setBlogListData(it)
                    }

                }
            }
        })

        viewModel.viewState.observe(this, Observer {viewState ->
            viewState.dataList?.let { countries ->
                println("DEBUG: Setting blog posts to RecyclerView: ${countries}")
                mainRecyclerAdapter.submitList(countries)
            }

        })
    }

    fun triggerGetCountriesEvent(){
        viewModel.setStateEvent(MainStateEvent.GetCountriesEvent())
    }


    fun handleDataStateChange(dataState: DataState<*>?){
        dataState?.let{
            // Handle loading
            showProgressBar(dataState.loading)

            // Handle Message
            dataState.message?.let{ event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(isVisible: Boolean){
        if(isVisible){
            binding.progress.visibility = View.VISIBLE
        }
        else{
            binding.progress.visibility = View.INVISIBLE
            Log.d("TIMESYSTEM", System.currentTimeMillis().toString())
        }
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    override fun onItemSelected(position: Int, item: Data) {
        println("DEBUG: CLICKED ${position}")
        println("DEBUG: CLICKED ${item}")
    }

    override fun onResume() {
        super.onResume()
    }
}