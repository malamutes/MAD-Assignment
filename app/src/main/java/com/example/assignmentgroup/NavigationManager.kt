package com.example.assignmentgroup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationManager(viewModel: GameViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.playerOrAIScreen,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        composable(route = Routes.playerOrAIScreen) {
            PlayerOrAIScreen(
                playerOrAiOption = DataSource.playerOrAIOptions,
                onNextButtonClicked = {
                    viewModel.setPlayerVsAI(it) /* referencing DataSource initial options struct */
                    navController.navigate(Routes.gridSizeScreen)
                }
            )
        }

        composable(route = Routes.gridSizeScreen) {
            GridSizeScreen(
                gridSizeOptions = DataSource.gridSizeOptions,
                onNextButtonClicked = {
                    viewModel.setGridSize(it)
                    navController.navigate(Routes.playerOneColourScreen)
                }
            )
        }

        composable(route = Routes.playerOneColourScreen) {
            PlayerColourScreen(
                colorOption = DataSource.colorOptions,
                player = uiState.playerOne,
                heading = "Choose Player One Colour",
                onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer) {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(Routes.playerTwoColourScreen)
                    }
                    else if(!viewModel.uiState.value.vsPlayer) {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(Routes.playerOneNameScreen)
                    }
                }
            )
        }

        composable(route = Routes.playerTwoColourScreen) {
            PlayerColourScreen(
                colorOption = DataSource.colorOptions,
                player = uiState.playerTwo,
                heading = "Choose Player Two Colour",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.playerOneNameScreen)
                }
            )
        }

        composable(route = Routes.playerOneNameScreen) {
            PlayerNameScreen(
                playerName = uiState.playerOne.playerName,
                player = uiState.playerOne,
                heading = "Choose Player One Name",
                onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(Routes.playerTwoNameScreen)
                    }
                    else if(!viewModel.uiState.value.vsPlayer)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(Routes.playerOneAvatarScreen)
                    }
                }
            )
        }
        composable(route = Routes.playerTwoNameScreen) {
            PlayerNameScreen(
                playerName = uiState.playerTwo.playerName,
                player = uiState.playerTwo,
                heading = "Choose Player Two Name",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.playerOneAvatarScreen)
                }
            )
        }

        composable(route = Routes.playerOneAvatarScreen) {
            PlayerAvatarScreen(
                avatar = DataSource.avatarImages,
                player = uiState.playerOne,
                heading = "Choose Player One Avatar",
                onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(Routes.playerTwoAvatarScreen)
                    }
                    else if(!viewModel.uiState.value.vsPlayer)
                    {
                        viewModel.updatePlayerOne(it)
                        /* making ai with random customizations */
                        viewModel.updatePlayerTwo(
                            Player(
                                pColor = DataSource.colorOptions.random(),
                                pName = "AI",
                                pAvatar = DataSource.avatarImages.random(),
                                pScore = 0,
                                pGrid = mutableListOf()
                            ),
                        )
                        navController.navigate(Routes.playerConfirmScreen)
                    }
                }
            )
        }

        composable(route = Routes.playerTwoAvatarScreen) {
            PlayerAvatarScreen(
                avatar = DataSource.avatarImages,
                player = uiState.playerTwo,
                heading = "Choose Player One Avatar",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.playerConfirmScreen)
                }
            )
        }

        composable(route = Routes.playerConfirmScreen) {
            if(!uiState.isGridMade) {
                viewModel.updateBoard(viewModel.initGridState(uiState.gridSizeScreen))
                viewModel.setIsGridMade(true)
            }
            PlayerConfirmScreen(
                player1 = uiState.playerOne,
                player2 = uiState.playerTwo,
                onNextButtonClicked = {
                    if(uiState.vsPlayer) {
                        navController.navigate(Routes.gamePlayingPlayerScreen)
                    }
                    else {
                        navController.navigate(Routes.gamePlayingAIScreen)
                    }
                }
            )
        }

        composable(route = Routes.gamePlayingPlayerScreen) {
            InGameScreen(
                isPlayerOne = uiState.isPlayerOne,
                gameBoard = uiState.gameBoard,
                player1 = uiState.playerOne,
                player2 = uiState.playerTwo,
                navController = navController,
                viewModel = viewModel,
                onNextButtonClicked = { /* need to type cast as an ANY list is returned from button click */
                    viewModel.updateBoard(it[0] as Board)
                    viewModel.updatePlayerOne(it[1] as Player)
                    viewModel.updatePlayerTwo(it[2] as Player)

                    viewModel.setIsPlayerOne(!uiState.isPlayerOne)

                    if(it[3] == false) {
                        navController.navigate(Routes.gamePlayingPlayerScreen)
                    }
                    else if(it[3] == true) {
                        navController.navigate(Routes.gameOverScreen)
                    }
                }
            )
        }

        composable(route = Routes.gamePlayingAIScreen) {
            InGameScreenAI(
                gameBoard = uiState.gameBoard,
                player1 = uiState.playerOne,
                player2 = uiState.playerTwo,
                navController = navController,
                viewModel = viewModel,
                onNextButtonClicked = { /* need to type cast as an ANY list is returned from button click */
                    viewModel.updateBoard(it[0] as Board)
                    viewModel.updatePlayerOne(it[1] as Player)
                    viewModel.updatePlayerTwo(it[2] as Player)

                    if(it[3] == false) {
                        navController.navigate(Routes.gamePlayingAIScreen)
                    }
                    else if(it[3] == true) {
                        navController.navigate(Routes.gameOverScreen)
                    }
                }
            )
        }

        composable(route = Routes.gameOverScreen) {
            GameOverScreen(
                playAgain = DataSource.playAgain,
                player1 = uiState.playerOne,
                player2 = uiState.playerTwo,
                mainMenuOnClick = {
                    resetBoard(viewModel, uiState)
                    navController.navigate(Routes.playerOrAIScreen)
                },
                replayOnClick = {
                    resetBoard(viewModel, uiState)
                    navController.navigate(Routes.playerConfirmScreen)
                }
            )
        }

        composable(route = Routes.settingsScreen) {
            InGameSettings(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Routes.p1NameToSettings) {
            PlayerNameScreen(
                playerName = uiState.playerOne.playerName,
                player = uiState.playerOne,
                heading = "Choose Player One Name",
                onNextButtonClicked = {
                    viewModel.updatePlayerOne(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }

        composable(route = Routes.p1AvatarToSettings) {
            PlayerAvatarScreen(
                avatar = DataSource.avatarImages,
                player = uiState.playerOne,
                heading = "Choose Player One Avatar",
                onNextButtonClicked = {
                    viewModel.updatePlayerOne(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }

        composable(route = Routes.p1ColourToSettings) {
            PlayerColourScreen(
                colorOption = DataSource.colorOptions,
                player = uiState.playerOne,
                heading = "Choose Player One Colour",
                onNextButtonClicked = {
                    viewModel.updatePlayerOne(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }

        composable(route = Routes.p2NameToSettings) {
            PlayerNameScreen(
                playerName = uiState.playerTwo.playerName,
                player = uiState.playerTwo,
                heading = "Choose Player Two Name",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }

        composable(route = Routes.p2AvatarToSettings) {
            PlayerAvatarScreen(
                avatar = DataSource.avatarImages,
                player = uiState.playerTwo,
                heading = "Choose Player Two Avatar",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }

        composable(route = Routes.p2ColourToSettings) {
            PlayerColourScreen(
                colorOption = DataSource.colorOptions,
                player = uiState.playerTwo,
                heading = "Choose Player Two Colour",
                onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(Routes.settingsScreen)
                }
            )
        }
    }
}

fun resetBoard(viewModel: GameViewModel, uiState: GameUIState) {
    uiState.playerOne.playerGrid.clear()
    uiState.playerTwo.playerGrid.clear()
    viewModel.setIsGridMade(false)
    uiState.gameBoard.boardFreeGrid = 0
    uiState.gameBoard.boardGrid.clear()
    viewModel.setIsPlayerOne(true)
    InGameStatsManager.moves = 0
    InGameStatsManager.playerOneTurn = true
    viewModel.updateBoard(viewModel.initGridState(uiState.gridSizeScreen))
    viewModel.setIsGridMade(true)
    MoveQueue.moveQueue = mutableListOf()
}