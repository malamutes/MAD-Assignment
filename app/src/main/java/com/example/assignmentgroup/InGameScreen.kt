package com.example.assignmentgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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

object InGameTurnManager {
    var playerOneTurn = true
}

/* 2d matrix with list in each entry for index and state, so 3d matrix*/
//so row, column and then inner most list is < index, state of index>

@Composable
fun inGameScreen(isPlayerOne: Boolean, gameBoard: Board, player1: Player, player2: Player,
                 onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */

    var boardGrid = gameBoard.boardGrid
    var boardFreeGrid = gameBoard.boardFreeGrid
    var playerOneGridTaken = player1.playerGrid
    var playerTwoGridTaken = player2.playerGrid

    var gameOverOut = false

    Column(

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(boardGrid[0].count()),
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
            /*tf this fixes it but doesnt even do anything*/
        ) {
            for (i in 0..< boardGrid.count()) {
                for (j in 0..< boardGrid[0].count()) {
                    if (boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player1.playerColor, onClick = { })
                        }
                    }
                    else if (boardGrid[i][j][1] == 2)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player2.playerColor, onClick = { })
                        }
                    }
                    else if (boardGrid[i][j][1] == 0) /* free circle to be occupied this is where core logic happens, the other if statements is just to render the existing game*/ {
                        items(1) { element ->
                            cardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                boardFreeGrid -= 1
                                if (isPlayerOne) {
                                    InGameTurnManager.playerOneTurn = false
                                    playerOneGridTaken.add(boardGrid[i][j][0])
                                    boardGrid[i][j][1] = 1

                                    if (i - 1 > -1) {
                                        boardGrid[i - 1][j][1] = 0
                                    }

                                    if ((gameBoard.consecutiveCheckers(playerOneGridTaken, gameBoard.boardGrid[0].count()))) {
                                        gameOverOut = true
                                        player1.updateScore()
                                    }
                                    else if (gameBoard.boardFreeGrid == 0) {
                                        gameOverOut = true
                                    }
                                    onNextButtonClicked(
                                        listOf(
                                            gameBoard,
                                            player1,
                                            player2,
                                            gameOverOut
                                        )
                                    )
                                }
                                else if (!isPlayerOne) {
                                    InGameTurnManager.playerOneTurn = true
                                    playerTwoGridTaken.add(boardGrid[i][j][0])
                                    boardGrid[i][j][1] = 2

                                    if (i - 1 > -1) {
                                        boardGrid[i - 1][j][1] = 0
                                    }

                                    if (gameBoard.consecutiveCheckers(
                                            playerTwoGridTaken,
                                            gameBoard.boardGrid[0].count()
                                        )
                                    ) {
                                        gameOverOut = true
                                        player2.updateScore()
                                    }
                                    else if (gameBoard.boardFreeGrid == 0) {
                                        gameOverOut = true
                                    }
                                    onNextButtonClicked(
                                        listOf(
                                            gameBoard,
                                            player1,
                                            player2,
                                            gameOverOut
                                        )
                                    )
                                }
                            })
                        }
                    }
                    else if (boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                        items(1) { element ->
                            cardMagentaRender(element = boardGrid[i][j][0], onClick = {})
                        }
                    }
                }
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            displayAvatars(player1, player2)
        }
    }
}


@Composable
fun inGameScreenAI(gameBoard: Board, player1: Player, player2: Player,
                   onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */

    var boardGrid = gameBoard.boardGrid
    var boardFreeGrid = gameBoard.boardFreeGrid
    var playerOneGridTaken = player1.playerGrid
    var playerTwoGridTaken = player2.playerGrid

    var boardFreeGridList = gameBoard.boardFreeGridList

    var gameOverOut = false

    Column(

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(boardGrid[0].count()),
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
            /*tf this fixes it but doesnt even do anything*/
        ) {
            for(i in 0..< boardGrid.count()) {
                for(j in 0..< boardGrid[0].count()) {
                    if(boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player1.playerColor, onClick = { })
                        }
                    }

                    else if(boardGrid[i][j][1] == 2)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(Color.Red, onClick = { })
                        }
                    }

                    else if(boardGrid[i][j][1] == 0) /* free circle to be occupied this is where core logic happens, the other if statements is just to render the existing game*/
                    {
                        boardFreeGridList.add(Pair(i, j))

                        items(1) { element ->
                            cardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                boardFreeGrid -= 1
                                playerOneGridTaken.add(boardGrid[i][j][0])
                                boardGrid[i][j][1] = 1

                                if(i - 1 > -1) {
                                    boardGrid[i - 1][j][1] = 0
                                    boardFreeGridList.add(Pair(i - 1, j))
                                }

                                boardFreeGridList.remove(Pair(i, j))

                                var aiPick = boardFreeGridList.random()

                                boardGrid[aiPick.first][aiPick.second][1] = 2
                                boardFreeGridList.remove(Pair(aiPick.first, aiPick.second))
                                playerTwoGridTaken.add(boardGrid[aiPick.first][aiPick.second][0])

                                if(aiPick.first - 1 > -1) {
                                    boardGrid[aiPick.first - 1][aiPick.second][1] = 0
                                    boardFreeGridList.add(Pair(aiPick.first - 1, aiPick.second))
                                }

                                if((gameBoard.consecutiveCheckers(playerOneGridTaken, gameBoard.boardGrid[0].count()))) {
                                    gameOverOut = true
                                    player1.updateScore()
                                }
                                else if((gameBoard.consecutiveCheckers(playerTwoGridTaken, gameBoard.boardGrid[0].count()))) {
                                    gameOverOut = true
                                    player1.updateScore()
                                }
                                else if(gameBoard.boardFreeGrid == 0) {
                                    gameOverOut = true
                                }

                                println(boardFreeGridList)
                                println(aiPick)
                                onNextButtonClicked(listOf(gameBoard, player1, player2, gameOverOut))
                            })
                        }
                    }
                    else if(boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                        items(1) { element ->
                            cardMagentaRender(element = boardGrid[i][j][0], onClick = { })
                        }
                    }
                }
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            displayAvatars(player1, player2)
        }
    }
}



@Composable
fun cardDefaultRender(element: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) {
        Text(
            text = element.toString(),
            fontSize = 1.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun cardMagentaRender(element: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) {
        Text(
            text = element.toString(),
            fontSize = 1.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun cardPlayerRender(color: Color, onClick: () -> Unit) {
    Button(
        onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
    ) {
        Text(
            text = "1",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun displayAvatars(player1: Player, player2: Player) {
    var avatar: Int = player1.playerAvatar
    if (InGameTurnManager.playerOneTurn) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 15.dp, y = 25.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.20f).dp)
                .clip(CircleShape)
                .border(2.dp, player1.playerColor, CircleShape)
        )

        avatar = player2.playerAvatar
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 15.dp, y = 25.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.20f).dp)
                .clip(CircleShape)
            /* .clickable {}*/
        )
    }
    else {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 15.dp, y = 25.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.20f).dp)
                .clip(CircleShape)
        )

        avatar = player2.playerAvatar
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 15.dp, y = 25.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.20f).dp)
                .clip(CircleShape)
                .border(2.dp, player2.playerColor, CircleShape)
            /* .clickable {}*/
        )
    }
}