package com.example.assignmentgroup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recyclerviewcompose.R

object InGameStatsManager {
    var playerOneTurn = true
    var moves = 0
}

data class MoveInfo(val pIIndex: Int, val pJIndex: Int,val pPrevious: Int) {
    var iIndex = pIIndex
    var jIndex = pJIndex
    var previous = pPrevious
}

object MoveQueue {
    var moveQueue = mutableListOf<MoveInfo>()
}

/* 2d matrix with list in each entry for index and state, so 3d matrix*/
//so row, column and then inner most list is < index, state of index>

@Composable
fun InGameScreen(isPlayerOne: Boolean, gameBoard: Board, player1: Player, player2: Player, navController: NavController, viewModel: GameViewModel, onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val boardGrid = gameBoard.boardGrid
    val playerOneGridTaken = player1.playerGrid
    val playerTwoGridTaken = player2.playerGrid

    var gameOverOut = false

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Play Game",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 60.dp, 0.dp, 10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(boardGrid[0].count()),
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .fillMaxWidth((0.8f))
                /*tf this fixes it but doesn't even do anything*/
            ) {
                for (i in 0..<boardGrid.count()) {
                    for (j in 0..<boardGrid[0].count()) {
                        if (boardGrid[i][j][1] == 1) {
                            items(1) {
                                CardPlayerRender(player1.playerColor.first)
                            }
                        } else if (boardGrid[i][j][1] == 2) {
                            items(1) {
                                CardPlayerRender(player2.playerColor.first)
                            }
                        } else if (boardGrid[i][j][1] == 0) {
                            items(1) {
                                CardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                    gameBoard.boardFreeGrid -= 1
                                    if (isPlayerOne) {
                                        InGameStatsManager.playerOneTurn = false
                                        InGameStatsManager.moves++
                                        playerOneGridTaken.add(boardGrid[i][j][0])
                                        MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                        boardGrid[i][j][1] = 1

                                        if (i - 1 > -1) {
                                            boardGrid[i - 1][j][1] = 0
                                        }

                                        if ((gameBoard.consecutiveCheckers(gameBoard, 1))) {
                                            gameOverOut = true
                                            player1.updateScore(true)
                                            player2.updateScore(false)
                                            Toast.makeText(
                                                context,
                                                "Player 1 Wins!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else if (gameBoard.boardFreeGrid == 0) {
                                            gameOverOut = true
                                            Toast.makeText(
                                                context,
                                                "It's a Draw!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        onNextButtonClicked(
                                            listOf(
                                                gameBoard,
                                                player1,
                                                player2,
                                                gameOverOut
                                            )
                                        )
                                    } else {
                                        InGameStatsManager.playerOneTurn = true
                                        InGameStatsManager.moves++
                                        playerTwoGridTaken.add(boardGrid[i][j][0])
                                        MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                        boardGrid[i][j][1] = 2

                                        if (i - 1 > -1) {
                                            boardGrid[i - 1][j][1] = 0
                                        }

                                        if (gameBoard.consecutiveCheckers(gameBoard, 2)) {
                                            gameOverOut = true
                                            player1.updateScore(false)
                                            player2.updateScore(true)
                                            Toast.makeText(context, "Player 2 Wins!", Toast.LENGTH_SHORT).show()
                                        } else if (gameBoard.boardFreeGrid == 0) {
                                            gameOverOut = true
                                            Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
                                            player1.draw()
                                            player2.draw()
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
                        } else if (boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                            items(1) {
                                CardMagentaRender(element = boardGrid[i][j][0], onClick = {})
                            }
                        }
                    }
                }
            }

            Text(
                text = "Moves: " + InGameStatsManager.moves,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                DisplayAvatars(navController, uiState, player1, player2)
            }

            SettingsButtonRow(navController, viewModel, gameBoard, player1, player2)
        }
    }
}


@Composable
fun InGameScreenAI(gameBoard: Board, player1: Player, player2: Player, navController: NavController, viewModel: GameViewModel, onNextButtonClicked: (List<Any>) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val boardGrid = gameBoard.boardGrid
    val playerOneGridTaken = player1.playerGrid
    val playerTwoGridTaken = player2.playerGrid

    val boardFreeGridList = gameBoard.boardFreeGridList

    var gameOverOut = false

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Play Game",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 60.dp, 0.dp, 10.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(boardGrid[0].count()),
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .fillMaxWidth((0.8f))
            ) {
                for (i in 0..<boardGrid.count()) {
                    for (j in 0..<boardGrid[0].count()) {
                        if (boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                            items(1) {
                                CardPlayerRender(player1.playerColor.first)
                            }
                        } else if (boardGrid[i][j][1] == 2)/* occupied by player 1*/ {
                            items(1) {
                                CardPlayerRender(player2.playerColor.first)
                            }
                        } else if (boardGrid[i][j][1] == 0) {
                            boardFreeGridList.add(Pair(i, j))

                            items(1) {
                                CardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                    InGameStatsManager.moves++
                                    gameBoard.boardFreeGrid -= 1
                                    playerOneGridTaken.add(boardGrid[i][j][0])
                                    MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                    boardGrid[i][j][1] = 1

                                    if (i - 1 > -1) {
                                        boardGrid[i - 1][j][1] = 0
                                        boardFreeGridList.add(Pair(i - 1, j))
                                    }

                                    boardFreeGridList.remove(Pair(i, j))

                                    val aiPick = boardFreeGridList.random()
                                    InGameStatsManager.moves++

                                    MoveQueue.moveQueue.add(MoveInfo(aiPick.first, aiPick.second, boardGrid[aiPick.first][aiPick.second][1]))
                                    boardGrid[aiPick.first][aiPick.second][1] = 2
                                    boardFreeGridList.remove(Pair(aiPick.first, aiPick.second))
                                    playerTwoGridTaken.add(boardGrid[aiPick.first][aiPick.second][0])

                                    if (aiPick.first - 1 > -1) {
                                        boardGrid[aiPick.first - 1][aiPick.second][1] = 0
                                        boardFreeGridList.add(Pair(aiPick.first - 1, aiPick.second))
                                    }

                                    if ((gameBoard.consecutiveCheckers(gameBoard, 1))) {
                                        gameOverOut = true
                                        Toast.makeText(context, "Player One Wins!", Toast.LENGTH_SHORT).show()
                                        player1.updateScore(true)
                                        player2.updateScore(false)
                                    }
                                    else if ((gameBoard.consecutiveCheckers(gameBoard, 2))) {
                                        gameOverOut = true
                                        player1.updateScore(false)
                                        Toast.makeText(context, "Player 2 Wins!", Toast.LENGTH_SHORT).show()
                                        player2.updateScore(true)
                                    }
                                    else if (gameBoard.boardFreeGrid == 0) {
                                        gameOverOut = true
                                        Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
                                        player1.draw()
                                        player2.draw()
                                    }

                                    onNextButtonClicked(
                                        listOf(
                                            gameBoard,
                                            player1,
                                            player2,
                                            gameOverOut
                                        )
                                    )
                                })
                            }
                        } else if (boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                            items(1) {
                                CardMagentaRender(element = boardGrid[i][j][0], onClick = { })
                            }
                        }
                    }
                }
            }

            Text(
                text = "Moves: " + InGameStatsManager.moves,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                DisplayAvatars(navController, uiState, player1, player2)
            }

            SettingsButtonRow(navController, viewModel, gameBoard, player1, player2)
        }
    }
}



@Composable
fun CardDefaultRender(element: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) { }
}

@Composable
fun CardMagentaRender(element: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) { }
}

@Composable
fun CardPlayerRender(color: Color) {
    Button(
        onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)
    ) { }
}

@Composable
fun DisplayAvatars(navController: NavController, uiState: GameUIState, player1: Player, player2: Player) {
    if (InGameStatsManager.playerOneTurn) {
        PlayerTurnAvatarImage(navController = navController, player = player1, uiState = uiState, isPlayerOne = true)
        NotPlayerTurnAvatarImage(navController = navController, player = player2, uiState = uiState, isPlayerOne = false)
    }
    else {
        NotPlayerTurnAvatarImage(navController = navController, player = player1, uiState = uiState, isPlayerOne = true)
        PlayerTurnAvatarImage(navController = navController, player = player2, uiState = uiState, isPlayerOne = false)
    }
}

@Composable
fun PlayerTurnAvatarImage(navController: NavController, uiState: GameUIState, player: Player, isPlayerOne: Boolean) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1NameToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2NameToInGame)
                }
        )

        WinLoseGamesPlayedText(player = player)

        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
                .clip(CircleShape)
                .border(2.dp, player.playerColor.first, CircleShape)
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1AvatarToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2AvatarToInGame)
                }
        )
    }
}

@Composable
fun NotPlayerTurnAvatarImage(navController: NavController, uiState: GameUIState, player: Player, isPlayerOne: Boolean) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1NameToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2NameToInGame)
                }
        )

        WinLoseGamesPlayedText(player = player)

        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 0.25f).dp)
                .clip(CircleShape)
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1AvatarToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2AvatarToInGame)
                }
        )
    }
}

@Composable
fun WinLoseGamesPlayedText(player: Player) {
    Text(
        text = "Wins: ${player.wins}",
        fontSize = 10.sp,
        color = Color.LightGray,
        fontFamily = FontFamily.Serif,
    )
    Text(
        text = "Loses: ${player.loses}",
        fontSize = 10.sp,
        color = Color.LightGray,
        fontFamily = FontFamily.Serif,
    )
    Text(
        text = "Games Played: ${player.gamesPlayed}",
        fontSize = 10.sp,
        color = Color.LightGray,
        fontFamily = FontFamily.Serif,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, (LocalConfiguration.current.screenWidthDp * 0.04f).dp)
    )
}

@Composable
fun SettingsButtonRow(navController: NavController, viewModel: GameViewModel, gameBoard: Board, player1: Player, player2: Player) {
    val size = LocalConfiguration.current.screenWidthDp * 0.2f
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(0.dp, (LocalConfiguration.current.screenWidthDp * 0.04f).dp)
    ) {
        UndoButton(navController, viewModel, gameBoard, player1, player2, size)
        ResetButton(navController, viewModel, size)
        SettingsButton(navController, size)
        HomeButton(navController, viewModel, size)
    }
}

@Composable
fun UndoButton(navController: NavController, viewModel: GameViewModel, gameBoard: Board, player1: Player, player2: Player, size: Float) {
    val uiState by viewModel.uiState.collectAsState()
    Image(
        painter = painterResource(id = R.drawable.undobutton),
        contentDescription = "Undo Button",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(
                width = size.dp,
                height = size.dp
            )
            .clickable {
                if (uiState.vsPlayer) {
                    undoVsPlayer(navController, gameBoard, player1, player2)
                } else {
                    undoVsAI(navController, gameBoard, player1, player2)
                }
            }
    )
}

@Composable
fun ResetButton(navController: NavController, viewModel: GameViewModel, size: Float) {
    val uiState by viewModel.uiState.collectAsState()

    Image(
        painter = painterResource(id = R.drawable.resetbutton),
        contentDescription = "Reset Button",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(
                width = size.dp,
                height = size.dp
            )
            .clickable {
                resetBoard(viewModel, uiState)
                if (uiState.vsPlayer) {
                    navController.navigate(Routes.gamePlayingPlayerScreen)
                } else {
                    navController.navigate(Routes.gamePlayingAIScreen)
                }
            }
    )
}

@Composable
fun SettingsButton(navController: NavController, size: Float) {
    Image(
        painter = painterResource(id = R.drawable.settingsimage),
        contentDescription = "Settings Button",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(
                width = size.dp,
                height = size.dp
            )
            .clickable {
                navController.navigate(Routes.settingsScreen)
            }
    )
}

@Composable
fun HomeButton(navController: NavController, viewModel: GameViewModel, size: Float) {
    val uiState by viewModel.uiState.collectAsState()

    Image(
        painter = painterResource(id = R.drawable.homebutton),
        contentDescription = "Home Button",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(
                width = size.dp,
                height = size.dp
            )
            .clickable {
                resetBoard(viewModel, uiState)
                if (uiState.vsPlayer) {
                    navController.navigate(Routes.gamePlayingPlayerScreen)
                } else {
                    navController.navigate(Routes.gamePlayingAIScreen)
                }
                navController.navigate(Routes.playerOrAIScreen)
            }
    )
}

fun undo(gameBoard: Board, player1: Player, player2: Player) {
    val boardGrid = gameBoard.boardGrid
    val playerOneGridTaken = player1.playerGrid
    val playerTwoGridTaken = player2.playerGrid
    val boardFreeGrid = gameBoard.boardFreeGridList

    if (MoveQueue.moveQueue.isNotEmpty()) {
        InGameStatsManager.moves--
        InGameStatsManager.playerOneTurn = !InGameStatsManager.playerOneTurn

        val undidMove = MoveQueue.moveQueue.removeLast()
        if (InGameStatsManager.playerOneTurn)
            playerOneGridTaken.removeLast()
        else
            playerTwoGridTaken.removeLast()
        gameBoard.boardFreeGrid++
        boardGrid[undidMove.iIndex][undidMove.jIndex][1] = undidMove.previous
        if (undidMove.iIndex - 1 > -1) {
            boardGrid[undidMove.iIndex - 1][undidMove.jIndex][1] = -1
            if (Pair(undidMove.iIndex - 1, undidMove.jIndex) in boardFreeGrid) {
                boardFreeGrid.remove(Pair(undidMove.iIndex - 1, undidMove.jIndex))
            }
        }
    }
}

fun undoVsPlayer(navController: NavController, gameBoard: Board, player1: Player, player2: Player) {
    if (MoveQueue.moveQueue.isNotEmpty()) {
        undo(gameBoard, player1, player2)
        navController.navigate(Routes.gamePlayingPlayerScreen)
    }
}

fun undoVsAI(navController: NavController, gameBoard: Board, player1: Player, player2: Player) {
    if (MoveQueue.moveQueue.isNotEmpty()) {
        undo(gameBoard, player1, player2)
        undo(gameBoard, player1, player2)
        navController.navigate(Routes.gamePlayingAIScreen)
    }
}

@Composable
fun HorizontalInGameScreen(isPlayerOne: Boolean, gameBoard: Board, player1: Player, player2: Player, navController: NavController, viewModel: GameViewModel, onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val boardGrid = gameBoard.boardGrid
    val playerOneGridTaken = player1.playerGrid
    val playerTwoGridTaken = player2.playerGrid

    var gameOverOut = false

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
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
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(1f)
            ) {
                Text(
                    text = "Play Game",
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(boardGrid[0].count()),
                    modifier = Modifier
                        .height((LocalConfiguration.current.screenHeightDp * 0.75f).dp)
                        .fillMaxWidth((0.8f))
                    /*tf this fixes it but doesn't even do anything*/
                ) {
                    for (i in 0..<boardGrid.count()) {
                        for (j in 0..<boardGrid[0].count()) {
                            if (boardGrid[i][j][1] == 1) {
                                items(1) {
                                    CardPlayerRender(player1.playerColor.first)
                                }
                            } else if (boardGrid[i][j][1] == 2) {
                                items(1) {
                                    CardPlayerRender(player2.playerColor.first)
                                }
                            } else if (boardGrid[i][j][1] == 0) {
                                items(1) {
                                    CardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                        gameBoard.boardFreeGrid -= 1
                                        if (isPlayerOne) {
                                            InGameStatsManager.playerOneTurn = false
                                            InGameStatsManager.moves++
                                            playerOneGridTaken.add(boardGrid[i][j][0])
                                            MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                            boardGrid[i][j][1] = 1

                                            if (i - 1 > -1) {
                                                boardGrid[i - 1][j][1] = 0
                                            }

                                            if ((gameBoard.consecutiveCheckers(gameBoard, 1))) {
                                                gameOverOut = true
                                                player1.updateScore(true)
                                                player2.updateScore(false)
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
                                        else {
                                            InGameStatsManager.playerOneTurn = true
                                            InGameStatsManager.moves++
                                            playerTwoGridTaken.add(boardGrid[i][j][0])
                                            MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                            boardGrid[i][j][1] = 2

                                            if (i - 1 > -1) {
                                                boardGrid[i - 1][j][1] = 0
                                            }

                                            if (gameBoard.consecutiveCheckers(gameBoard, 2)) {
                                                gameOverOut = true
                                                player1.updateScore(false)
                                                player2.updateScore(true)
                                                Toast.makeText(context, "Player 2 Wins!", Toast.LENGTH_SHORT).show()
                                            } else if (gameBoard.boardFreeGrid == 0) {
                                                gameOverOut = true
                                                Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
                                                player1.draw()
                                                player2.draw()
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
                            } else if (boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                                items(1) {
                                    CardMagentaRender(element = boardGrid[i][j][0], onClick = {})
                                }
                            }
                        }
                    }
                }
            }

            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(1f)
            ) {
                Text(
                    text = "Moves: " + InGameStatsManager.moves,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                ) {
                    HorizontalDisplayAvatars(navController, uiState, player1, player2)
                }

                HorizontalSettingsButtonRow(navController, viewModel, gameBoard, player1, player2)
            }
        }
    }
}

@Composable
fun HorizontalInGameScreenAI(gameBoard: Board, player1: Player, player2: Player, navController: NavController, viewModel: GameViewModel, onNextButtonClicked: (List<Any>) -> Unit) { /* dunno if any is an abuse see if theres better ways later on */
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val boardGrid = gameBoard.boardGrid
    val playerOneGridTaken = player1.playerGrid
    val playerTwoGridTaken = player2.playerGrid

    val boardFreeGridList = gameBoard.boardFreeGridList

    var gameOverOut = false

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
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
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(1f)
            ) {
                Text(
                    text = "Play Game",
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(boardGrid[0].count()),
                    modifier = Modifier
                        .height((LocalConfiguration.current.screenHeightDp * 0.75f).dp)
                        .fillMaxWidth((0.8f))
                    /*tf this fixes it but doesn't even do anything*/
                ) {
                    for (i in 0..<boardGrid.count()) {
                        for (j in 0..<boardGrid[0].count()) {
                            if (boardGrid[i][j][1] == 1)/* occupied by player 1*/ {
                                items(1) {
                                    CardPlayerRender(player1.playerColor.first)
                                }
                            } else if (boardGrid[i][j][1] == 2) {
                                items(1) {
                                    CardPlayerRender(player2.playerColor.first)
                                }
                            } else if (boardGrid[i][j][1] == 0) {
                                boardFreeGridList.add(Pair(i, j))

                                items(1) {
                                    CardDefaultRender(element = boardGrid[i][j][0], onClick = {
                                        InGameStatsManager.moves++
                                        gameBoard.boardFreeGrid -= 1
                                        playerOneGridTaken.add(boardGrid[i][j][0])
                                        MoveQueue.moveQueue.add(MoveInfo(i, j, boardGrid[i][j][1]))
                                        boardGrid[i][j][1] = 1

                                        if (i - 1 > -1) {
                                            boardGrid[i - 1][j][1] = 0
                                            boardFreeGridList.add(Pair(i - 1, j))
                                        }

                                        boardFreeGridList.remove(Pair(i, j))

                                        val aiPick = boardFreeGridList.random()
                                        InGameStatsManager.moves++

                                        MoveQueue.moveQueue.add(MoveInfo(aiPick.first, aiPick.second, boardGrid[aiPick.first][aiPick.second][1]))
                                        boardGrid[aiPick.first][aiPick.second][1] = 2
                                        boardFreeGridList.remove(Pair(aiPick.first, aiPick.second))
                                        playerTwoGridTaken.add(boardGrid[aiPick.first][aiPick.second][0])

                                        if (aiPick.first - 1 > -1) {
                                            boardGrid[aiPick.first - 1][aiPick.second][1] = 0
                                            boardFreeGridList.add(Pair(aiPick.first - 1, aiPick.second))
                                        }

                                        if ((gameBoard.consecutiveCheckers(gameBoard, 1))) {
                                            gameOverOut = true
                                            Toast.makeText(context, "Player One Wins!", Toast.LENGTH_SHORT).show()
                                            player1.updateScore(true)
                                            player2.updateScore(false)
                                        }
                                        else if ((gameBoard.consecutiveCheckers(gameBoard, 2))) {
                                            gameOverOut = true
                                            player1.updateScore(false)
                                            Toast.makeText(context, "Player 2 Wins!", Toast.LENGTH_SHORT).show()
                                            player2.updateScore(true)
                                        }
                                        else if (gameBoard.boardFreeGrid == 0) {
                                            gameOverOut = true
                                            Toast.makeText(context, "It's a Draw!", Toast.LENGTH_SHORT).show()
                                            player1.draw()
                                            player2.draw()
                                        }

                                        onNextButtonClicked(
                                            listOf(
                                                gameBoard,
                                                player1,
                                                player2,
                                                gameOverOut
                                            )
                                        )
                                    })
                                }
                            } else if (boardGrid[i][j][1] == -1) /* locked circle to be occupied */ {
                                items(1) {
                                    CardMagentaRender(element = boardGrid[i][j][0], onClick = { })
                                }
                            }
                        }
                    }
                }
            }

            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(1f)
            ) {
                Text(
                    text = "Moves: " + InGameStatsManager.moves,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(0.dp, 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                ) {
                    HorizontalDisplayAvatars(navController, uiState, player1, player2)
                }

                HorizontalSettingsButtonRow(navController, viewModel, gameBoard, player1, player2)
            }
        }
    }
}

@Composable
fun HorizontalDisplayAvatars(navController: NavController, uiState: GameUIState, player1: Player, player2: Player) {
    if (InGameStatsManager.playerOneTurn) {
        HorizontalPlayerTurnAvatarImage(navController = navController, player = player1, uiState = uiState, isPlayerOne = true)
        HorizontalNotPlayerTurnAvatarImage(navController = navController, player = player2, uiState = uiState, isPlayerOne = false)
    }
    else {
        HorizontalNotPlayerTurnAvatarImage(navController = navController, player = player1, uiState = uiState, isPlayerOne = true)
        HorizontalPlayerTurnAvatarImage(navController = navController, player = player2, uiState = uiState, isPlayerOne = false)
    }
}

@Composable
fun HorizontalPlayerTurnAvatarImage(navController: NavController, uiState: GameUIState, player: Player, isPlayerOne: Boolean) {
    var widthFraction = 1f
    if (isPlayerOne)
        widthFraction = 0.5f
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(widthFraction)
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1NameToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2NameToInGame)
                }
        )

        WinLoseGamesPlayedText(player = player)

        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenHeightDp * 0.25f).dp)
                .clip(CircleShape)
                .border(2.dp, player.playerColor.first, CircleShape)
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1AvatarToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2AvatarToInGame)
                }
        )
    }
}

@Composable
fun HorizontalNotPlayerTurnAvatarImage(navController: NavController, uiState: GameUIState, player: Player, isPlayerOne: Boolean) {
    var widthFraction = 1f
    if (isPlayerOne)
        widthFraction = 0.5f
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(widthFraction)
    ) {
        Text(
            text = player.playerName,
            fontSize = 25.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1NameToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2NameToInGame)
                }
        )

        WinLoseGamesPlayedText(player = player)

        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerAvatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size((LocalConfiguration.current.screenHeightDp * 0.25f).dp)
                .clip(CircleShape)
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1AvatarToInGame)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2AvatarToInGame)
                }
        )
    }
}

@Composable
fun HorizontalSettingsButtonRow(navController: NavController, viewModel: GameViewModel, gameBoard: Board, player1: Player, player2: Player) {
    val size = LocalConfiguration.current.screenHeightDp * 0.15f
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(0.dp, (LocalConfiguration.current.screenHeightDp * 0.04f).dp)
    ) {
        UndoButton(navController, viewModel, gameBoard, player1, player2, size)
        ResetButton(navController, viewModel, size)
        SettingsButton(navController, size)
        HomeButton(navController, viewModel, size)
    }
}