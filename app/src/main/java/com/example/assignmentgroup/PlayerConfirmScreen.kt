package com.example.assignmentgroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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


@Composable
fun PlayerConfirmScreen(player1: Player, player2: Player,
                        onNextButtonClicked: () -> Unit){

        Column(modifier = Modifier.padding(50.dp)) {
            PlayerConfirm(avatar = player1.playerAvatar, name = player1.playerName, color = player1.playerColor) {}
            PlayerConfirm(avatar = player2.playerAvatar, name = player2.playerName, color = player2.playerColor) {}
            confirmButton(onClick = {onNextButtonClicked()})
        }
}

@Composable
fun PlayerConfirm(avatar: Int, name: String, color: Color,  onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .aspectRatio(2f)
        .offset(y = (LocalConfiguration.current.screenHeightDp * 0.025f).dp)
        .padding((LocalConfiguration.current.screenHeightDp * 0.01f).dp)
        .border(
            width = 2.5.dp,
            color = MaterialTheme.colorScheme.scrim,
            shape = MaterialTheme.shapes.extraSmall
        )
        .background(color = Color.Black),
        horizontalArrangement = Arrangement.spacedBy(25.dp)) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.15f).dp)
                .clip(CircleShape)
                .border(BorderStroke(5.dp, color), shape = CircleShape)
        )
        Column {
            Text(text = name, color = color)
            Text(text = color.toString(), color = color)
        }
    }
}

@Composable
fun confirmButton(onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .padding(1.dp)) {
        Text(text = "Confirm", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}
