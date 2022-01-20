package com.example.hw_6.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.hw_6.NodeApplication
import com.example.hw_6.databinding.ActivityMainBinding
import com.example.hw_6.viewmodels.NodeViewModel
import com.example.hw_6.viewmodels.NodeFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProviders.of(
            this,
            NodeFactory((application as NodeApplication).repository)
        )[NodeViewModel::class.java]
    }
}