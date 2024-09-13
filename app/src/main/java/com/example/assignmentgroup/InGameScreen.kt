package com.example.assignmentgroup

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
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

object InGameStatsManager {
    var playerOneTurn = true
    var moves = 0
}

/* 2d matrix with list in each entry for index and state, so 3d matrix*/
//so row, column and then inner most list is < index, state of index>

@Composable
fun inGameScreen(isPlayerOne: Boolean, gameBoard: Board, player1: Player, player2: Player,
                 onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */

    val context = LocalContext.current
    var boardGrid = gameBoard.boardGrid
    var boardFreeGrid = gameBoard.boardFreeGrid
    var playerOneGridTaken = player1.playerGrid
    var playerTwoGridTaken = player2.playerGrid

    var gameOverOut = false

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(boardGrid[0].count()),
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
                .fillMaxWidth((0.8f))
            /*tf this fixes it but doesn't even do anything*/
        ) {
            for (i in 0..< boardGrid.count()) {
                for (j in 0..< boardGrid[0].count()) {
                    if (boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player1.playerColor.first, onClick = { })
                        }
                    }
                    else if (boardGrid[i][j][1] == 2)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player2.playerColor.first, onClick = { })
                        }
                    }
                    else if (boardGrid[i][j][1] == 0) /* free circle to be occupied this is where core logic happens, the other if statements is just to render the existing game*/ {
                        items(1) { element ->
                            cardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                boardFreeGrid -= 1
                                if (isPlayerOne) {
                                    InGameStatsManager.playerOneTurn = false
                                    InGameStatsManager.moves++
                                    playerOneGridTaken.add(boardGrid[i][j][0])
                                    boardGrid[i][j][1] = 1

                                    if (i - 1 > -1) {
                                        boardGrid[i - 1][j][1] = 0
                                    }

                                    if ((gameBoard.consecutiveCheckers(playerOneGridTaken, gameBoard.boardGrid[0].count()))) {
                                        gameOverOut = true
                                        player1.updateScore()
                                        Toast.makeText(context, "Player 1 Wins!", Toast.LENGTH_SHORT).show()
                                    }
                                    else if (gameBoard.boardFreeGrid == 0) {
                                        gameOverOut = true
                                        Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
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
                                    InGameStatsManager.playerOneTurn = true
                                    InGameStatsManager.moves++
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
                                        Toast.makeText(context, "Player 2 Wins!", Toast.LENGTH_SHORT).show()
                                    }
                                    else if (gameBoard.boardFreeGrid == 0) {
                                        gameOverOut = true
                                        Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
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

        Text(
            text = "Moves: " + InGameStatsManager.moves,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        )

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            DisplayAvatars(player1, player2)
        }

        SettingsButtonRow()
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

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(boardGrid[0].count()),
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
            /*tf this fixes it but doesn't even do anything*/
        ) {
            for(i in 0..< boardGrid.count()) {
                for(j in 0..< boardGrid[0].count()) {
                    if(boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                        items(1) { element ->
                            cardPlayerRender(player1.playerColor.first, onClick = { })
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
                                InGameStatsManager.moves++
                                boardFreeGrid -= 1
                                playerOneGridTaken.add(boardGrid[i][j][0])
                                boardGrid[i][j][1] = 1

                                if(i - 1 > -1) {
                                    boardGrid[i - 1][j][1] = 0
                                    boardFreeGridList.add(Pair(i - 1, j))
                                }

                                boardFreeGridList.remove(Pair(i, j))

                                var aiPick = boardFreeGridList.random()
                                InGameStatsManager.moves++

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
            DisplayAvatars(player1, player2)
        }

        SettingsButtonRow()
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
            .size(50.dp)
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
fun DisplayAvatars(player1: Player, player2: Player) {
    if (InGameStatsManager.playerOneTurn) {
        PlayerTurnAvatarImage(player = player1)
        NotPlayerTurnAvatarImage(player = player2)
    }
    else {
        NotPlayerTurnAvatarImage(player = player1)
        PlayerTurnAvatarImage(player = player2)
    }
}

@Composable
fun PlayerTurnAvatarImage(player: Player) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(5.dp)
        )
        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
                .clip(CircleShape)
                .border(2.dp, player.playerColor.first, CircleShape)
            /* .clickable {}*/
        )
    }
}

@Composable
fun NotPlayerTurnAvatarImage(player: Player) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(5.dp)
        )
        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun SettingsButtonRow() {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        UndoButton()
        ResetButton()
        SettingsButton()
        HomeButton()
    }
}

@Composable
fun UndoButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = CircleShape,
        modifier = Modifier
            .padding(1.dp)
            .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.undobutton),
            contentDescription = "Undo Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
        )
    }
}

@Composable
fun ResetButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = CircleShape,
        modifier = Modifier
            .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.resetbutton),
            contentDescription = "Undo Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
        )
    }
}

@Composable
fun SettingsButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = CircleShape,
        modifier = Modifier
            .padding(1.dp)
            .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.settingsimage),
            contentDescription = "Undo Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
        )
    }
}

@Composable
fun HomeButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = CircleShape,
        modifier = Modifier
            .padding(1.dp)
            .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.homebutton),
            contentDescription = "Undo Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
        )
    }
}