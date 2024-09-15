package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color

class Player(pColor: Pair<Color, String>, pName: String, pAvatar: Int, pWins: Int, pLoses: Int, pGamesPlayed: Int, pGrid: MutableList<Int>) {
    var playerColor = pColor
    var playerName = pName
    var playerAvatar = pAvatar
    var wins = pWins
    var loses = pLoses
    var gamesPlayed = pGamesPlayed
    var playerGrid = pGrid

    fun updateScore(won: Boolean) {
        if (won)
            wins++
        else
            loses++

        gamesPlayed++
    }

    fun draw() {
        gamesPlayed++
    }
}