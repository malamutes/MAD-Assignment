package com.example.assignmentgroup

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recyclerviewcompose.R


var avatarImages2 = listOf(
    R.drawable.vik,
    R.drawable.ekko,
    R.drawable.jayce,
    R.drawable.jinx,
    R.drawable.silco,
    R.drawable.lucy,
)

/* 2d matrix with list in each entry for index and state, so 3d matrix*/
//so row, column and then inner most list is < index, state of index>

@Composable
fun inGameScreen(isPlayerOne: Boolean, gameBoard: Board, player1: Player, player2: Player,
                 onNextButtonClicked: (List<Any>) -> Unit){ /* dunno if any is an abuse see if theres better ways later on */

    var boardGrid = gameBoard.boardGrid
    var boardFreeGrid = gameBoard.boardFreeGrid
    var playerOneGridTaken = player1.playerGrid
    var playerTwoGridTaken = player2.playerGrid

    var gameOverOut = false

    LazyVerticalGrid(columns = GridCells.Fixed(boardGrid[0].count()), modifier = Modifier.height(1.dp)
    /*tf this fixes it but doesnt even do anything*/) {
        for(i in 0..boardGrid.count()-1)
        {
            for(j in 0..boardGrid[0].count()-1)
            {
                if(boardGrid[i][j][1] == 1)/* occupied by player 1*/
                {
                    items(1) { element ->
                        cardPlayerRender(player1.playerColor, onClick = { })
                    }
                }

                else if(boardGrid[i][j][1] == 2)/* occupied by player 1*/
                {
                    items(1) { element ->
                        cardPlayerRender(player2.playerColor, onClick = { })
                    }
                }

                else if(boardGrid[i][j][1] == 0) /* free circle to be occupied this is where core logic happens, the other if statements is just to render the existing game*/
                {
                    items(1) { element ->
                        cardDefaultRender(element = boardGrid[i][j][0], onClick = {
                            boardFreeGrid -= 1
                            if(isPlayerOne == true)
                            {
                                playerOneGridTaken.add(boardGrid[i][j][0])
                                boardGrid[i][j][1] = 1
                                if(i - 1 > -1)
                                {
                                    boardGrid[i - 1][j][1] = 0
                                }
                                if((gameBoard.consecutiveCheckers(playerOneGridTaken, gameBoard.boardGrid[0].count()) == true))
                                {
                                    gameOverOut = true
                                    player1.updateScore()
                                }
                                else if(gameBoard.boardFreeGrid == 0)
                                {
                                    gameOverOut = true
                                }
                                println(playerOneGridTaken)
                                onNextButtonClicked(listOf(gameBoard, player1, player2, gameOverOut))
                            }
                            else if(isPlayerOne == false)
                            {
                                playerTwoGridTaken.add(boardGrid[i][j][0])
                                boardGrid[i][j][1] = 2
                                if(i - 1 > -1)
                                {
                                    boardGrid[i - 1][j][1] = 0
                                }
                                if((gameBoard.consecutiveCheckers(playerTwoGridTaken, gameBoard.boardGrid[0].count()) == true))
                                {
                                    gameOverOut = true
                                    player2.updateScore()
                                }

                                else if(gameBoard.boardFreeGrid == 0)
                                {
                                    gameOverOut = true
                                }
                                println(playerTwoGridTaken)
                                onNextButtonClicked(listOf(gameBoard, player1, player2, gameOverOut))
                            }
                        })
                    }
                }
                else if(boardGrid[i][j][1] == -1) /* locked circle to be occupied */
                {
                    items(1) { element ->
                        cardMagentaRender(element = boardGrid[i][j][0], onClick = {})
                    }
                }
            }
        }
    }
}

@Composable
fun cardDefaultRender(element: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

@Composable
fun cardMagentaRender(element: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

@Composable
fun cardPlayerRender(color: Color, onClick: () -> Unit){
    Button(onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = "1", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}


