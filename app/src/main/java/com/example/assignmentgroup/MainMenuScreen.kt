package com.example.assignmentgroup

import android.provider.ContactsContract.Data
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recyclerviewcompose.R

enum class GameScreen(val title: String) {
    PlayerOrAI(title = "PlayerOrAI"),
    GridSize(title = "GridSize"),
    PlayerOneColour(title = "PlayerOneColour"),
    PlayerTwoColour(title = "PlayerTwoColour"),
    PlayerOneName(title = "PlayerOneName"),
    PlayerTwoName(title = "PlayerTwoName"),
    PlayerOneAvatar(title = "PlayerOneAvatar"),
    PlayerTwoAvatar(title = "Pl ayerTwoAvatar"),
    PlayerConfirm(title = "PlayerConfirm"),
    GamePlaying(title = "GamePlaying"),
    GameOverScreen(title = "GameOver")
}


/* navigation top bar which allows to go back if back navigation is possible */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameAppBar(
    currentScreen: GameScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(text = currentScreen.title) },
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack)
            {
                IconButton(onClick = { navigateUp }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Composable
fun GameApp(
    viewModel: GameViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
    ){
//    println("asdadasd")  /* to do with very first composable i have */
    /*recomposing multiple times so printing multiple times */
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = GameScreen.valueOf(
        backStackEntry?.destination?.route ?: GameScreen.PlayerOrAI.name)

    Scaffold(
        topBar = {
            GameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = (navController.previousBackStackEntry != null),
                navigateUp = { navController.navigateUp() }
            )
        },
    )
    { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = GameScreen.PlayerOrAI.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = GameScreen.PlayerOrAI.name){
                PlayerOrAIScreen(
                    playerOrAiOption = DataSource.playerOrAIOptions,
                    onNextButtonClicked = {
                        viewModel.setPlayerVsAI(it) /* referencing DataSource initial options struct */
                        navController.navigate(GameScreen.GridSize.name)}
                )
            }
            composable(route = GameScreen.GridSize.name){
                GridSizeScreen(
                    gridSizeOptions = DataSource.gridSizeOptions,
                    onNextButtonClicked = {
                        viewModel.setGridSize(it)
                        navController.navigate(GameScreen.PlayerOneColour.name)}
                )
            }
            composable(route = GameScreen.PlayerOneColour.name){
                PlayerColourScreen(
                    colorOption = DataSource.colorOptions,
                    player = uiState.playerOne,
                    onNextButtonClicked = {
                        if(viewModel.uiState.value.vsPlayer == true)
                        {
                            /*if there are two players then set first player go to second and from second go next screen
                            this ensures nav path always works correctnly (i think) as it is always
                             prev -> firplayer -> secplayer -> next
                             */
                            viewModel.updatePlayerOne(it)
                            navController.navigate(GameScreen.PlayerTwoColour.name)
                        }
                        else if(viewModel.uiState.value.vsPlayer == false)
                        {
                            viewModel.updatePlayerOne(it)
                            navController.navigate(GameScreen.PlayerOneName.name)
                        }
                    }
                )
            }
            composable(route = GameScreen.PlayerTwoColour.name){
                PlayerColourScreen(
                    colorOption = DataSource.colorOptions,
                    player = uiState.playerTwo,
                    onNextButtonClicked = {
                        viewModel.updatePlayerTwo(it)
                        navController.navigate(GameScreen.PlayerOneName.name)
                    }
                )
            }
            composable(route = GameScreen.PlayerOneName.name){
                PlayerNameScreen(playerName = uiState.playerOne.playerName, player = uiState.playerOne, onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer == true)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(GameScreen.PlayerTwoName.name)
                    }
                    else if(viewModel.uiState.value.vsPlayer == false)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(GameScreen.PlayerOneAvatar.name)
                    }
                })
            }
            composable(route = GameScreen.PlayerTwoName.name){
                PlayerNameScreen(playerName = uiState.playerTwo.playerName, player = uiState.playerTwo,
                    onNextButtonClicked = {
                        viewModel.updatePlayerTwo(it)
                        navController.navigate(GameScreen.PlayerOneAvatar.name)
                    }
                )
            }
            composable(route = GameScreen.PlayerOneAvatar.name){
                PlayerAvatarScreen(avatar = DataSource.avatarImages, player = uiState.playerOne, onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer == true)
                    {
                        viewModel.updatePlayerOne(it)
                        navController.navigate(GameScreen.PlayerTwoAvatar.name)
                    }
                    else if(viewModel.uiState.value.vsPlayer == false)
                    {
                        viewModel.updatePlayerOne(it)
                        /* making ai with random customizations */
                        viewModel.updatePlayerTwo(Player(pColor = DataSource.colorOptions.random(),
                            pName = "AI", pAvatar = DataSource.avatarImages.random(), pScore = 0, pGrid = mutableListOf()), )
                        navController.navigate(GameScreen.PlayerConfirm.name)
                    }
                })
            }

            composable(route = GameScreen.PlayerTwoAvatar.name){
                PlayerAvatarScreen(avatar = DataSource.avatarImages, player = uiState.playerTwo, onNextButtonClicked = {
                    viewModel.updatePlayerTwo(it)
                    navController.navigate(GameScreen.PlayerConfirm.name)
                })
            }

            composable(route = GameScreen.PlayerConfirm.name){
                if(uiState.isGridMade == false)
                {
                    viewModel.updateBoard(viewModel.initGridState(uiState.gridSizeScreen))
                    viewModel.setIsGridMade(true)
                }
                PlayerConfirmScreen(player1 = uiState.playerOne, player2 = uiState.playerTwo,
                                    onNextButtonClicked = {navController.navigate(GameScreen.GamePlaying.name)
                                    }
                )
            }

            composable(route = GameScreen.GamePlaying.name){
                inGameScreen(isPlayerOne = uiState.isPlayerOne, gameBoard = uiState.gameBoard, player1 = uiState.playerOne, player2 = uiState.playerTwo,
                    onNextButtonClicked = { /* need to type cast as an ANY list is returned from button click */
                        viewModel.updateBoard(it[0] as Board)
                        viewModel.updatePlayerOne(it[1] as Player)
                        viewModel.updatePlayerTwo(it[2] as Player)

                        viewModel.setIsPlayerOne(!uiState.isPlayerOne)

                        if(it[3] == false)
                        {
                            navController.navigate(GameScreen.GamePlaying.name)
                        }
                        else if(it[3] == true)
                        {
                            navController.navigate(GameScreen.GameOverScreen.name)
                        }
                    })
            }
            composable(route = GameScreen.GameOverScreen.name){
                GameOverScreen(
                    playAgain = DataSource.playAgain,
                    player1 = uiState.playerOne,
                    player2 = uiState.playerTwo,
                    onNextButtonClicked = {
                        uiState.playerOne.playerGrid.clear()
                        uiState.playerTwo.playerGrid.clear()
                        viewModel.setIsGridMade(false)
                        uiState.gameBoard.boardFreeGrid = 0
                        uiState.gameBoard.boardGrid.clear()

                        if(it == true)
                        {
                            navController.navigate(GameScreen.PlayerConfirm.name)
                        }
                        else if(it == false)
                        {
                            /* need to implement return to main menu immediately with path */
                            navController.navigate(GameScreen.PlayerOrAI.name)
                        }
                    })
            }
            composable(route = GameScreen.PlayerOrAI.name){
                PlayerOrAIScreen(
                    playerOrAiOption = DataSource.playerOrAIOptions,
                    onNextButtonClicked = {
                        viewModel.setPlayerVsAI(it) /* referencing DataSource initial options struct */
                        navController.navigate(GameScreen.GridSize.name)}
                )
            }
        }
    }
}


