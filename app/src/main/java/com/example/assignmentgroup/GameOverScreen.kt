package com.example.assignmentgroup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp


@Composable
fun GameOverScreen(gameBoard: Board, player1: Player, player2: Player, portrait: Boolean, mainMenuOnClick: () -> Unit,  replayOnClick: () -> Unit) {
    InGameStatsManager.moves = 0

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        if (portrait) {
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
                    text = player1.wins.toString() + ":" + player2.wins.toString(),
                    fontSize = 100.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                val boardGrid = gameBoard.boardGrid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(boardGrid[0].count()),
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .fillMaxWidth((0.8f))
                ) {
                    for (i in 0..< boardGrid.count()) {
                        for (j in 0..< boardGrid[0].count()) {
                            items(1) {
                                BoardCounter(
                                    boardValue = boardGrid[i][j][1],
                                    player1Colour = player1.playerColor.first,
                                    player2Colour = player2.playerColor.first
                                )
                            }
                        }
                    }
                }
            }
        }
        else {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize(1f)
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
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
                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
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
                        text = player1.wins.toString() + ":" + player2.wins.toString(),
                        fontSize = 100.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                val boardGrid = gameBoard.boardGrid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(boardGrid[0].count()),
                    modifier = Modifier
                        .size(min((LocalConfiguration.current.screenHeightDp * 0.8f).dp, (LocalConfiguration.current.screenWidthDp * 0.4f).dp))
                ) {
                    for (i in 0..< boardGrid.count()) {
                        for (j in 0..< boardGrid[0].count()) {
                            items(1) {
                                BoardCounter(
                                    boardValue = boardGrid[i][j][1],
                                    player1Colour = player1.playerColor.first,
                                    player2Colour = player2.playerColor.first
                                )
                            }
                        }
                    }
                }
            }
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

@Composable
fun BoardCounter(boardValue: Int, player1Colour: Color, player2Colour: Color) {
    var colour: Color = Color(0xFFF500FF)
    if (boardValue == 1)
        colour = player1Colour
    else if (boardValue == 2)
        colour = player2Colour

    Button(
        onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(colour),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) { }
}


