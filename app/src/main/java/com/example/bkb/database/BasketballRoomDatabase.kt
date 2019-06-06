package com.example.bkb.database

import android.content.Context
import androidx.room.*
import com.example.bkb.database.converters.Converters
import com.example.bkb.database.daos.MatchDAO
import com.example.bkb.database.entities.Match
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Match::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BasketballRoomDatabase : RoomDatabase() {

    abstract fun matchDAO(): MatchDAO

    companion object {
        @Volatile
        private var INSTANCE: BasketballRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BasketballRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, BasketballRoomDatabase::class.java, "BKBDB")
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}