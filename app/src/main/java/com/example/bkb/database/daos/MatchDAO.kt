package com.example.bkb.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bkb.database.entities.Match

@Dao
interface MatchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match:Match)

    @Query("SELECT * FROM matches ORDER BY date DESC")
    fun getAllMatch(): LiveData<List<Match>>

    @Update
    suspend fun updateMatch(match: Match)

}