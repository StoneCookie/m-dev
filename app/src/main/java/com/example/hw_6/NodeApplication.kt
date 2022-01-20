package com.example.hw_6

import android.app.Application
import com.example.hw_6.database.NodeDatabase
import com.example.hw_6.repository.NodeRepository

class NodeApplication : Application() {
    val database by lazy { NodeDatabase.getDatabase(this) }
    val repository by lazy { NodeRepository(database.nodeDao()) }
}