package com.example.basketball.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var teamA: String,
    var teamB: String,
    var scoreA: Int,
    var scoreB: Int,
    var date: Date,
    var isOver: Boolean
) {
}