package com.example.hw_6.database

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "node_table")
data class Node(
    @PrimaryKey(autoGenerate = true) @NonNull
    val id: Long,

    @ColumnInfo
    val value: String,

    @ColumnInfo
    val nodes: MutableList<Node>
)