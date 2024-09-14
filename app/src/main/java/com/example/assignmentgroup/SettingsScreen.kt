package com.example.assignmentgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InGameSettings(navController: NavController, viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTitle()
            PlayerCustomisableGroup(
                navController = navController,
                player = uiState.playerOne,
                isPlayerOne = true
            )
            if (uiState.vsPlayer) {
                PlayerCustomisableGroup(
                    navController = navController,
                    player = uiState.playerTwo,
                    isPlayerOne = false
                )
            }
            CloseButton(navController = navController, uiState = uiState)
        }
    }
}

@Composable
fun SettingsTitle() {
    Text(
        text = "Settings",
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        modifier = Modifier
            .fillMaxWidth(1f)
    )
}

@Composable 
fun PlayerCustomisableGroup(navController: NavController, player: Player, isPlayerOne: Boolean) {
    CustomisePlayerTitle(title = "Customise Player One")
    DisplayPlayerName(navController = navController, playerName = player.playerName, isPlayerOne = isPlayerOne)
    ColourAndAvatarRow(navController = navController, player = player, isPlayerOne = isPlayerOne)
}

@Composable
fun CustomisePlayerTitle(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(0.dp, 60.dp, 0.dp, 5.dp)
    )
}

@Composable
fun DisplayPlayerName(navController: NavController, playerName: String, isPlayerOne: Boolean) {
    Text(
        text = playerName,
        textAlign = TextAlign.Center,
        color = Color.LightGray,
        fontSize = 25.sp,
        fontFamily = FontFamily.Serif,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(0.dp, 5.dp, 0.dp, 0.dp)
            .clickable {
                if (isPlayerOne)
                    navController.navigate(Routes.p1NameToSettings)
                else
                    navController.navigate(Routes.p2NameToSettings)
            }
    )
}

@Composable
fun ColourAndAvatarRow(navController: NavController, player: Player, isPlayerOne: Boolean) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        DisplayPlayerColour(navController = navController, player = player, isPlayerOne = isPlayerOne)
        DisplayPlayerAvatar(navController = navController, player = player, isPlayerOne = isPlayerOne)
    }
}

@Composable
fun DisplayPlayerColour(navController: NavController, player: Player, isPlayerOne: Boolean) {
    Button (
        onClick = {
            if (isPlayerOne)
                navController.navigate(Routes.p1ColourToSettings)
            else
                navController.navigate(Routes.p2ColourToSettings)
        },
        colors = ButtonDefaults.buttonColors(player.playerColor.first),
        shape = CircleShape,
        modifier = Modifier
            .size(LocalConfiguration.current.screenWidthDp.dp * 0.25f)
            .padding(25.dp, 5.dp)
            .aspectRatio(1f)
    ) { }
}

@Composable
fun DisplayPlayerAvatar(navController: NavController, player: Player, isPlayerOne: Boolean) {    
    Image(
        painter = painterResource(id = player.playerAvatar), 
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(LocalConfiguration.current.screenWidthDp.dp * 0.25f)
            .size(100.dp)
            .padding(25.dp, 5.dp)
            .clip(CircleShape)
            .aspectRatio(1f)
            .clickable {
                if (isPlayerOne)
                    navController.navigate(Routes.p1AvatarToSettings)
                else
                    navController.navigate(Routes.p2AvatarToSettings)
            }
    )
}

@Composable
fun CloseButton(navController: NavController, uiState: GameUIState) {
    Button (
        onClick = {
            if (uiState.vsPlayer)
                navController.navigate(Routes.gamePlayingPlayerScreen)
            else
                navController.navigate(Routes.gamePlayingAIScreen)
        },
        colors = ButtonDefaults.buttonColors(Color.LightGray)
    ) {
        Text (
            text = "Close Settings",
            fontFamily = FontFamily.Serif
        )
    }
}