package com.example.assignmentpaul

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

    fun setIsPlayerOne(isP1: Boolean){
        _uiState.update { currentState -> currentState.copy(isPlayerOne = isP1) }
    }
}