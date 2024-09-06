package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color
import com.example.recyclerviewcompose.R

data class GameUIState(
    var vsPlayer: Boolean = false,
    var gridSizeScreen: Int = 0,
    var isPlayerOne: Boolean = true,
    var isGridMade: Boolean = false,
//    val gameState: MutableList<MutableList<MutableList<Int>>> = mutableListOf<MutableList<MutableList<Int>>>(),
//    val freeGrids: MutableList<Int> = mutableListOf(),

    /*initial players */
    var playerOne: Player = Player(
        pColor = Color.Red,
        pName = "Enter your name",
        pAvatar = R.drawable.vik,
        pScore = 0,
        pGrid = mutableListOf()
    ),

    var playerTwo: Player = Player(
        pColor = Color.Blue,
        pName = "Enter your name",
        pAvatar = R.drawable.silco,
        pScore = 0,
        pGrid = mutableListOf()
    ),

    var gameBoard: Board = Board(
        gameBoard = (mutableListOf (mutableListOf(mutableListOf()))),
        freeGrid = 0,
        freeGridList = mutableSetOf()
    )

)

