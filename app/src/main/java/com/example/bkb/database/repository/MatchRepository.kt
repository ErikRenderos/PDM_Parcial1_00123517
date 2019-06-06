package com.example.bkb.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.bkb.database.daos.MatchDAO
import com.example.bkb.database.entities.Match

class MatchRepository(private val matchDAO: MatchDAO) {

    val getAllMatch: LiveData<List<Match>> = matchDAO.getAllMatch()

    @WorkerThread
    suspend fun insertMatch(match: Match){
        matchDAO.insertMatch(match)
    }

    @WorkerThread
    suspend fun updateMatch(match: Match){
        matchDAO.updateMatch(match)
    }

}