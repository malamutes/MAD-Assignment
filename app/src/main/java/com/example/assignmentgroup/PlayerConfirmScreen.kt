package com.example.assignmentgroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp


@Composable
fun PlayerConfirmScreen(player1: Player, player2: Player, onNextButtonClicked: () -> Unit) {
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

            PlayerConfirm(
                avatar = player1.playerAvatar,
                name = player1.playerName,
                color = player1.playerColor
            ) {}
            PlayerConfirm(
                avatar = player2.playerAvatar,
                name = player2.playerName,
                color = player2.playerColor
            ) {}

            Text(
                text = player1.playerScore.toString() + ":" + player2.playerScore.toString(),
                fontSize = 100.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(10.dp)
            )

            confirmButton(onClick = { onNextButtonClicked() })
        }
    }
}

@Composable
fun PlayerConfirm(avatar: Int, name: String, color: Pair<Color, String>,  onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .aspectRatio(2f)
            .offset(y = (LocalConfiguration.current.screenHeightDp * 0.025f).dp)
            .padding((LocalConfiguration.current.screenHeightDp * 0.01f).dp)
            .border(
                width = 2.5.dp,
                color = MaterialTheme.colorScheme.scrim,
                shape = MaterialTheme.shapes.extraSmall,
            )
            .background(color = Color.Black),
        horizontalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.15f).dp)
                .clip(CircleShape)
                .border(BorderStroke(5.dp, color.first), shape = CircleShape)
        )
        Column {
            Text(text = name, color = color.first)
            Text(text = color.second, color = color.first)
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
            .padding(1.dp)) {
        Text(
            text = "Confirm",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}
