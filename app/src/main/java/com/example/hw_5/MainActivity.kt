package com.example.hw_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_5.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

interface TaskCallbacks{
    fun getPerson(person: Person)
}

class MainActivity : AppCompatActivity(), TaskCallbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private var fragment: MyAsync? = null

    override fun getPerson(person: Person) {
        Log.d("my_tag", "Get")
        adapter.addPerson(person)
    }

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

        val fragmentManager = supportFragmentManager
        val oldFragment = fragmentManager.findFragmentByTag(MyAsync.MyTag)
        if (oldFragment == null){
            fragment = MyAsync()
            fragmentManager.beginTransaction().add(fragment!!, MyAsync.MyTag).commit()
        }
        else{
            fragment = oldFragment as MyAsync
            adapter.getPreviousPersons(fragment!!.person)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rcView.layoutManager = layoutManager
            rcView.adapter = adapter
        }
    }
}