package com.example.hw_6.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Insert
    fun insert(node: Node): Long

    @Update
    fun update(node: Node)

    @Query("SELECT * FROM node_table")
    fun getAll(): Flow<List<Node>>

    @Query("SELECT * from node_table WHERE id = :key")
    fun get(key: Long): Node?
}