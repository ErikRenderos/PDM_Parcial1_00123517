package com.example.basketball.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.basketball.database.daos.MatchDAO
import com.example.basketball.database.entities.Match

class MatchRepository(private val matchDao: MatchDAO) {

    val getAllMatches: LiveData<List<Match>> = matchDao.getAllMatches()

    @WorkerThread
    suspend fun insertMatch(match: Match){
        matchDao.insert(match)
    }

    @WorkerThread
    suspend fun updateMatch(match: Match){
        matchDao.updateMatch(match)
    }

}