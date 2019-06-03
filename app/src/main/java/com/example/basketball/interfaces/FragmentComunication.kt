package com.example.basketball.interfaces

import com.example.basketball.models.MatchViewModel

interface FragmentComunication {
    fun addMatch()
    fun viewMatches()
    fun sendData(pos: Int)
}