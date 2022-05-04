package com.ifgoiano.mvipattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.ifgoiano.mvipattern.databinding.ActivityHomeBinding
import com.ifgoiano.mvipattern.util.Values

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonApi.setOnClickListener{setToInvisible()}
        binding.buttonLocal.setOnClickListener{startLocalActivity()}
        binding.buttonRemote.setOnClickListener{startRemoteActivity()}

        binding.buttonLeve.setOnClickListener{
            Values.BASE_URL_ID = 1
            startAPIActivity()
            setToVisible()
        }

        binding.buttonMedio.setOnClickListener{
            Values.BASE_URL_ID = 2
            startAPIActivity()
            setToVisible()
        }

        binding.buttonPesado.setOnClickListener{
            Values.BASE_URL_ID = 3
            startAPIActivity()
            setToVisible()
        }


    }

    fun setToInvisible(){
        binding.buttonApi.visibility = Button.GONE
        binding.buttonLocal.visibility = Button.GONE
        binding.buttonRemote.visibility = Button.GONE

        binding.buttonLeve.visibility = Button.VISIBLE
        binding.buttonMedio.visibility = Button.VISIBLE
        binding.buttonPesado.visibility = Button.VISIBLE

    }

    fun setToVisible(){
        binding.buttonApi.visibility = Button.VISIBLE
        binding.buttonLocal.visibility = Button.VISIBLE
        binding.buttonRemote.visibility = Button.VISIBLE

        binding.buttonLeve.visibility = Button.GONE
        binding.buttonMedio.visibility = Button.GONE
        binding.buttonPesado.visibility = Button.GONE
    }

    fun startAPIActivity(){
        startActivity(Intent(this, APIActivity::class.java))
    }

    fun startLocalActivity(){ startActivity(Intent(this, LocalActivity::class.java)) }

    fun startRemoteActivity(){ startActivity(Intent(this, RemoteActivity::class.java)) }

}