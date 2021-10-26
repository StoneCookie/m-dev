package com.example.hw_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

interface ActivityFunctions {
    fun cartEvent(e: String)
    fun likeEvent(e: String)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

        adapter = PersonAdapter(object : ActivityFunctions{
            override fun cartEvent(e: String) {
                Snackbar.make(binding.root, "Нажата карточка: $e", 1000).show()
            }
            override fun likeEvent(e: String){
                Snackbar.make(binding.root, "Нажат лайк: $e", 1000).show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rcView.layoutManager = layoutManager
            rcView.adapter = adapter
        }
    }
}