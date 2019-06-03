package com.example.basketball.models

import android.app.Application
import androidx.lifecycle.*
import com.example.basketball.database.MatchRoomDatabase
import com.example.basketball.database.entities.Match
import com.example.basketball.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MatchViewModel(app: Application): AndroidViewModel(app) {
    val repository: MatchRepository
    val database: MatchRoomDatabase
    var getAllMatches: LiveData<List<Match>>
    lateinit var currentMatch: MediatorLiveData<Match>

    init {
        database = MatchRoomDatabase.getDatabase(app, viewModelScope)
        val matchDao = database.matchDao()
        repository = MatchRepository(matchDao)
        getAllMatches = repository.getAllMatches
        currentMatch = MediatorLiveData()
        currentMatch.value = Match(0,"A", "B", 0, 0, Calendar.getInstance().time, false)
    }

    fun insertMatch(match: Match) =  viewModelScope.launch(Dispatchers.IO){
        repository.insertMatch(match)
    }

    fun updateMatch(match: Match) =  viewModelScope.launch(Dispatchers.IO){
        repository.updateMatch(match)
    }

    fun startNewMatch(newMatch: Match){
        currentMatch.value = newMatch
    }
}