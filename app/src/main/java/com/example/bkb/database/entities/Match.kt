package com.example.bkb.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var homeTeam: String,
    var guestTeam: String,
    var homeScore: Int,
    var guestScore: Int,
    var date: Date,
    var isOver: Boolean
) {
}