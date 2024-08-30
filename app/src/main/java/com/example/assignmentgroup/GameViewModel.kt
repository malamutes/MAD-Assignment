package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    fun setPlayerVsAI(pva: Boolean){
        _uiState.update { currentState -> currentState.copy(vsPlayer = pva) }
    }

    fun setGridSize(size: Int){
        _uiState.update { currentstate -> currentstate.copy(gridSizeScreen = size) }
    }

    fun setIsGridMade(gridMade: Boolean){
        _uiState.update { currentState -> currentState.copy(isGridMade = gridMade) }
    }


    fun updateBoard(gameBoard: Board){
        _uiState.update { currentState -> currentState.copy(gameBoard = gameBoard) }
    }

    fun setIsPlayerOne(isP1: Boolean){
        _uiState.update { currentState -> currentState.copy(isPlayerOne = isP1) }
    }

    fun updatePlayerOne(player: Player){
        _uiState.update { currentState -> currentState.copy(playerOne = player) }
    }

    fun updatePlayerTwo(player: Player){
        _uiState.update { currentState -> currentState.copy(playerTwo = player) }
    }

//    fun setFreeGrids(setFG: MutableList<Int>){
//        _uiState.update { currentState -> currentState.copy(freeGrids = setFG) }
//    }

    fun initGridState(gridSizeOptions: Int): Board{
        var freeGrid: Int = 0
        var gameState: MutableList<MutableList<MutableList<Int>>> = mutableListOf<MutableList<MutableList<Int>>>()
        if(gridSizeOptions == 0){ /* standard board 7 by 6 */
            val row: Int = 7
            val column: Int = 6
            val initState: Int = -1
            val initStateFree: Int = 0
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    if(i == row -1)
                    {
                        rowList.add(mutableListOf(index, initStateFree))
                        index += 1
                    }
                    else
                    {
                        rowList.add(mutableListOf(index, initState))
                        index += 1
                    }
                }
                gameState.add(rowList)
            }
            freeGrid = row * column
        }
        if(gridSizeOptions == 1){ /* small board 6 by 5 */
            val row: Int = 6
            val column: Int = 5
            val initState: Int = -1
            val initStateFree: Int = 0
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    if(i == row -1)
                    {
                        rowList.add(mutableListOf(index, initStateFree))
                        index += 1
                    }
                    else
                    {
                        rowList.add(mutableListOf(index, initState))
                        index += 1
                    }
                }
                gameState.add(rowList)
            }
            freeGrid = row * column
        }
        else if(gridSizeOptions == 2){ /* large board 8 by 7 */
            val row: Int = 8
            val column: Int = 7
            val initState: Int = -1
            val initStateFree: Int = 0
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    if(i == row -1)
                    {
                        rowList.add(mutableListOf(index, initStateFree))
                        index += 1
                    }
                    else
                    {
                        rowList.add(mutableListOf(index, initState))
                        index += 1
                    }
                }
                gameState.add(rowList)
            }
            freeGrid = row * column
        }

        var board: Board = Board(gameBoard = gameState, freeGrid = freeGrid, freeGridList = mutableSetOf())
        return board
    }
}