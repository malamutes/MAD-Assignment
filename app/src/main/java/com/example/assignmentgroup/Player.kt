package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color

class Player(pColor: Pair<Color, String>, pName: String, pAvatar: Int, pScore: Int, pGrid: MutableList<Int>) {
    var playerColor = pColor
    var playerName = pName
    var playerAvatar = pAvatar
    var playerScore = pScore
    var playerGrid = pGrid

    fun updateScore() {
        playerScore += 1
    }
}