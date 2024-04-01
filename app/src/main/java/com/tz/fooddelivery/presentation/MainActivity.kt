package com.tz.fooddelivery.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        initToolbar()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val cityName = toolbar?.findViewById<Spinner>(R.id.city_name)

        val adapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.cities,
                R.layout.spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cityName?.adapter = adapter
    }

    fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}