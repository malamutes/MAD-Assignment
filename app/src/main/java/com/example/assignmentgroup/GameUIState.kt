package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color

data class GameUIState(
    var vsPlayer: Boolean = false,
    var gridSizeScreen: Int = 0,
    var playerOneColor: Color,
    var playerTwoColor: Color,
    var playerOneName: String = "Enter your name",
    var playerTwoName: String = "Enter your name",
    var gameScore: List<Int> = listOf(0,0),
    var isPlayerOne: Boolean = true,
    var playerOneAvatar: Int = 0,
    var playerTwoAvatar: Int = 0,
    var isGridMade: Boolean = false,
    val gameState: MutableList<MutableList<MutableList<Int>>> = mutableListOf<MutableList<MutableList<Int>>>(),
    val freeGrids: MutableList<Int> = mutableListOf(),
    var gameOver: Boolean = false
)