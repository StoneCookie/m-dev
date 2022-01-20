package com.example.hw_6.database

import android.content.Context
import androidx.room.*

@Database(entities = [Node::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class NodeDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao

    companion object {
        @Volatile
        private var nodeDB: NodeDatabase? = null

        fun getDatabase(context: Context): NodeDatabase {
            return nodeDB?:synchronized(this) {
                val case = Room.databaseBuilder (
                    context.applicationContext,
                    NodeDatabase::class.java,
                    "node_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                nodeDB = case
                case
            }
        }
    }
}
