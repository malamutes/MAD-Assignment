package com.example.assignmentgroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun PlayerConfirmScreen(navController: NavController, uiState: GameUIState, portrait: Boolean, player1: Player, player2: Player, onNextButtonClicked: () -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column(
            modifier = Modifier
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Confirm Choices",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 0.dp, 0.dp, 20.dp)
            )

            if (portrait) {
                PlayerConfirm(
                    navController = navController,
                    uiState = uiState,
                    portrait = portrait,
                    player = player1,
                    isPlayerOne = true
                )
                PlayerConfirm(
                    navController = navController,
                    uiState = uiState,
                    portrait = portrait,
                    player = player2,
                    isPlayerOne = false
                )

                Text(
                    text = player1.wins.toString() + ":" + player2.wins.toString(),
                    fontSize = 100.sp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(10.dp)
                )

                confirmButton(onClick = { onNextButtonClicked() })
            }
            else {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    PlayerConfirm(
                        navController = navController,
                        uiState = uiState,
                        portrait = portrait,
                        player = player1,
                        isPlayerOne = true
                    )
                    PlayerConfirm(
                        navController = navController,
                        uiState = uiState,
                        portrait = portrait,
                        player = player2,
                        isPlayerOne = false
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = player1.wins.toString() + ":" + player2.wins.toString(),
                        fontSize = 100.sp,
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(10.dp)
                    )

                    confirmButton(onClick = { onNextButtonClicked() })
                }
            }
        }
    }
}

@Composable
fun PlayerConfirm(navController: NavController, uiState: GameUIState, portrait: Boolean, player: Player, isPlayerOne: Boolean) {
    var rowWidth = (LocalConfiguration.current.screenWidthDp * 0.8f).dp
    if (!portrait)
        rowWidth = (LocalConfiguration.current.screenWidthDp * 0.4f).dp

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(rowWidth)
            .aspectRatio(2f)
            .offset(y = (LocalConfiguration.current.screenHeightDp * 0.025f).dp)
            .padding((LocalConfiguration.current.screenHeightDp * 0.01f).dp)
            .border(
                width = (2.5).dp,
                color = MaterialTheme.colorScheme.scrim,
                shape = MaterialTheme.shapes.extraSmall,
            )
            .background(color = Color.Black),
        horizontalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Image(
            painter = painterResource(id = player.playerAvatar),
            contentDescription = player.playerName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .fillMaxHeight(0.8f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(BorderStroke(5.dp, player.playerColor.first), shape = CircleShape)
                .clickable {
                    if (isPlayerOne)
                        navController.navigate(Routes.p1AvatarToConfirm)
                    else
                        if (uiState.vsPlayer)
                            navController.navigate(Routes.p2AvatarToConfirm)
                }
        )
        Column {
            Text (
                text = player.playerName,
                color = player.playerColor.first,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .clickable {
                        if (isPlayerOne)
                            navController.navigate(Routes.p1NameToConfirm)
                        else
                            if (uiState.vsPlayer)
                                navController.navigate(Routes.p2NameToConfirm)
                    }
            )

            Text (
                text = player.playerColor.second,
                color = player.playerColor.first,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .clickable {
                        if (isPlayerOne)
                            navController.navigate(Routes.p1ColourToConfirm)
                        else
                            if (uiState.vsPlayer)
                                navController.navigate(Routes.p2ColourToConfirm)
                    }
            )
        }
    }
}

@Composable
fun confirmButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
        modifier = Modifier
            .offset(y = 15.dp)
            .padding(1.dp)
    ) {
        Text(
            text = "Confirm",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}
