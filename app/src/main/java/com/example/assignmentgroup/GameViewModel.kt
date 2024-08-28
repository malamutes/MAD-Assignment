package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(GameUIState(playerOneColor = Color.Red, playerTwoColor = Color.Blue))
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    fun setPlayerVsAI(pva: Boolean){
        _uiState.update { currentState -> currentState.copy(vsPlayer = pva) }
    }

    fun setGridSize(size: Int){
        _uiState.update { currentstate -> currentstate.copy(gridSizeScreen = size) }
    }

    fun setPlayerOneColor(col1: Color){
        _uiState.update { currentState -> currentState.copy(playerOneColor = col1) }
    }

    fun setPlayerTwoColor(col2: Color){
        _uiState.update { currentState -> currentState.copy(playerTwoColor = col2) }
    }

    fun setPlayerOneName(name1: String){
        _uiState.update { currentState -> currentState.copy(playerOneName = name1) }
    }

    fun setPlayerTwoName(name2: String){
        _uiState.update { currentState -> currentState.copy(playerTwoName = name2) }
    }

    fun setPlayerOneAvatar(avatar1: Int){
        _uiState.update { currentState -> currentState.copy(playerOneAvatar = avatar1) }
    }

    fun setPlayerTwoAvatar(avatar2: Int){
        _uiState.update { currentState -> currentState.copy(playerTwoAvatar = avatar2) }
    }

    fun setIsGridMade(gridMade: Boolean){
        _uiState.update { currentState -> currentState.copy(isGridMade = gridMade) }
    }

    fun setGameOver(gameOver: Boolean){
        _uiState.update { currentState -> currentState.copy(gameOver = gameOver) }
    }

    fun setGameState(gameState: MutableList<MutableList<MutableList<Int>>>){
        _uiState.update { currentState -> currentState.copy(gameState = gameState) }
    }

    fun setIsPlayerOne(isP1: Boolean){
        _uiState.update { currentState -> currentState.copy(isPlayerOne = isP1) }
    }

    fun initGridState(gridSizeOptions: Int): MutableList<MutableList<MutableList<Int>>>{
        var gameState: MutableList<MutableList<MutableList<Int>>> = mutableListOf<MutableList<MutableList<Int>>>()
        if(gridSizeOptions == 0){ /* standard board 7 by 6 */
            val row: Int = 7
            val column: Int = 6
            val initState: Int = -1
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    rowList.add(mutableListOf(index, initState))
                    index += 1
                }
                gameState.add(rowList)
            }
        }
        if(gridSizeOptions == 1){ /* small board 6 by 5 */
            val row: Int = 6
            val column: Int = 5
            val initState: Int = -1
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    rowList.add(mutableListOf(index, initState))
                    index += 1
                }
                gameState.add(rowList)
            }
        }
        else if(gridSizeOptions == 2){ /* large board 8 by 7 */
            val row: Int = 8
            val column: Int = 7
            val initState: Int = -1
            var index: Int = 0
            for(i in 0..row -1 ){
                val rowList = mutableListOf<MutableList<Int>>()
                for(j in 0..column -1)
                {
                    rowList.add(mutableListOf(index, initState))
                    index += 1
                }
                gameState.add(rowList)
            }
        }
        return gameState
    }

}