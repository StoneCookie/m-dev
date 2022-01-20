package com.example.hw_6.repository

import com.example.hw_6.database.Node
import com.example.hw_6.database.NodeDao

class NodeRepository(private val nodeDao: NodeDao) {

    val nodes = nodeDao.getAll()

    @Suppress("RedundantSuspendModifier")
    suspend fun getNode(id: Long): Node? {
        return nodeDao.get(id)
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun insert(node: Node): Long {
        return nodeDao.insert(node)
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun update(node: Node) {
        nodeDao.update(node)
    }
}