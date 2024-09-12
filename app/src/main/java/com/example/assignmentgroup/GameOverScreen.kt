package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GameOverScreen(playAgain: List<Pair<String, Boolean>>, player1: Player, player2: Player,
                     onNextButtonClicked: (Boolean) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        playAgain.forEach() {
            options -> GameOverButton(
                gameOver = options.first,
                onClick = { onNextButtonClicked(options.second) }
            )
        }
        Text(
            text = player1.playerScore.toString() + ":" + player2.playerScore.toString(),
            fontSize = 100.sp
        )
    }
}

@Composable
fun GameOverButton(gameOver: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .padding(25.dp)
    ) {
        Text(
            text = gameOver,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
        )
    }
}


