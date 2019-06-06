package com.example.bkb.database.models

import android.app.Application
import androidx.lifecycle.*
import com.example.bkb.database.BasketballRoomDatabase
import com.example.bkb.database.entities.Match
import com.example.bkb.database.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MatchViewModel(app: Application): AndroidViewModel(app) {
    val repository: MatchRepository
    val database: BasketballRoomDatabase
    var getAllMatch: LiveData<List<Match>>
    lateinit var currentMatch: MediatorLiveData<Match>

    init {
        database = BasketballRoomDatabase.getDatabase(app, viewModelScope)
        val matchDAO = database.matchDAO()
        repository = MatchRepository(matchDAO)
        getAllMatch = repository.getAllMatch
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