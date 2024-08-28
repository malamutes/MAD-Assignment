package com.example.assignmentgroup

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
    GamePlaying(title = "GamePlaying")
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
                    onNextButtonClicked = {
                        if(viewModel.uiState.value.vsPlayer == true)
                        {
                            /*if there are two players then set first player go to second and from second go next screen
                            this ensures nav path always works correctnly (i think) as it is always
                             prev -> firplayer -> secplayer -> next
                             */
                            viewModel.setPlayerOneColor(it)
                            navController.navigate(GameScreen.PlayerTwoColour.name)
                        }
                        else if(viewModel.uiState.value.vsPlayer == false)
                        {
                            viewModel.setPlayerOneColor(it)
                            navController.navigate(GameScreen.PlayerOneName.name)
                        }
                    }
                )
            }
            composable(route = GameScreen.PlayerTwoColour.name){
                PlayerColourScreen(
                    colorOption = DataSource.colorOptions,
                    onNextButtonClicked = {
                        viewModel.setPlayerTwoColor(it)
                        navController.navigate(GameScreen.PlayerOneName.name)
                    }
                )
            }
            composable(route = GameScreen.PlayerOneName.name){
                PlayerNameScreen(uiState.playerOneName, onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer == true)
                    {
                        viewModel.setPlayerOneName(it)
                        navController.navigate(GameScreen.PlayerTwoName.name)
                    }
                    else if(viewModel.uiState.value.vsPlayer == false)
                    {
                        viewModel.setPlayerOneName(it)
                        navController.navigate(GameScreen.PlayerOneAvatar.name)
                    }
                })
            }
            composable(route = GameScreen.PlayerTwoName.name){
                PlayerNameScreen(uiState.playerTwoName,
                    onNextButtonClicked = {
                        viewModel.setPlayerTwoName(it)
                        navController.navigate(GameScreen.PlayerOneAvatar.name)
                    }
                )
            }
            composable(route = GameScreen.PlayerOneAvatar.name){
                PlayerAvatarScreen(avatar = DataSource.avatarImages, onNextButtonClicked = {
                    if(viewModel.uiState.value.vsPlayer == true)
                    {
                        viewModel.setPlayerOneAvatar(it)
                        navController.navigate(GameScreen.PlayerTwoAvatar.name)
                    }
                    else if(viewModel.uiState.value.vsPlayer == false)
                    {
                        viewModel.setPlayerOneAvatar(it)
                        navController.navigate(GameScreen.PlayerConfirm.name)
                    }
                })
            }
            composable(route = GameScreen.PlayerTwoAvatar.name){
                PlayerAvatarScreen(avatar = DataSource.avatarImages, onNextButtonClicked = {
                    viewModel.setPlayerTwoAvatar(it)
                    navController.navigate(GameScreen.PlayerConfirm.name)
                })
            }

            composable(route = GameScreen.PlayerConfirm.name){
                PlayerConfirmScreen(avatar1 = uiState.playerOneAvatar,
                                    name1 = uiState.playerOneName,
                                    color1 = uiState.playerOneColor,
                                    avatar2 = uiState.playerTwoAvatar,
                                    name2 = uiState.playerTwoName,
                                    color2 = uiState.playerTwoColor,
                                    vsPlayer = uiState.vsPlayer,
                                    onNextButtonClicked = {navController.navigate(GameScreen.GamePlaying.name)
                    })
            }
            composable(route = GameScreen.GamePlaying.name){
                if(uiState.isGridMade == false)
                {
                    viewModel.setGameState(viewModel.initGridState(uiState.gridSizeScreen))
                    viewModel.setIsGridMade(false)
                }
                inGameScreen(gameState = uiState.gameState, isGameOver = uiState.gameOver,
                    onNextButtonClicked = { /* need to type cast as an ANY list is returned from button click */
                        viewModel.setGameState(it[0] as MutableList<MutableList<MutableList<Int>>>)
                        viewModel.setGameOver(it[1] as Boolean)
                        if(uiState.gameOver == false){
                            navController.navigate(GameScreen.GamePlaying.name)
                        }
                    })
            }
        }
    }
}


