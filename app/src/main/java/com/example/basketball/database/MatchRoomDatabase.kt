package com.example.basketball.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basketball.database.converters.Converters
import com.example.basketball.database.daos.MatchDAO
import com.example.basketball.database.entities.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Match::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MatchRoomDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDAO

    companion object {
        @Volatile
        private var INSTANCE: MatchRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MatchRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, MatchRoomDatabase::class.java, "Bkb_database")
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}