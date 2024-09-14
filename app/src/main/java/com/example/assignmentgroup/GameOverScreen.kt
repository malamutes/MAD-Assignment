package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GameOverScreen(playAgain: List<Pair<String, Boolean>>, player1: Player, player2: Player, mainMenuOnClick: () -> Unit,  replayOnClick: () -> Unit) {
    InGameStatsManager.moves = 0

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Game Over",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 0.dp, 0.dp, 20.dp)
            )

            GameOverButton(
                gameOver = "Main Menu",
                onClick = mainMenuOnClick
            )

            GameOverButton(
                gameOver = "Replay",
                onClick = replayOnClick
            )

            Text(
                text = player1.playerScore.toString() + ":" + player2.playerScore.toString(),
                fontSize = 100.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
fun GameOverButton(gameOver: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(25.dp)
    ) {
        Text(
            text = gameOver,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color.LightGray
        )
    }
}


