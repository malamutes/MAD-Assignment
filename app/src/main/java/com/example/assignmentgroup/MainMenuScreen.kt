package com.example.assignmentgroup

import androidx.compose.runtime.Composable

enum class GameScreen(val title: String) {
    PlayerOrAI(title = "PlayerOrAI"),
    GridSize(title = "GridSize"),
    PlayerOneColour(title = "PlayerOneColour"),
    PlayerTwoColour(title = "PlayerTwoColour"),
    PlayerOneName(title = "PlayerOneName"),
    PlayerTwoName(title = "PlayerTwoName"),
    PlayerOneAvatar(title = "PlayerOneAvatar"),
    PlayerTwoAvatar(title = "PlayerTwoAvatar"),
    PlayerConfirm(title = "PlayerConfirm"),
    GamePlaying(title = "GamePlaying"),
    GamePlayingAI(title = "GamePlayingAI"),
    GameOverScreen(title = "GameOver")
}

@Composable
fun GameApp(viewModel: GameViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        NavigationManager(viewModel)
}


