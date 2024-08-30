package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerNameScreen(playerName: String, player: Player, onNextButtonClicked: (Player) -> Unit){
    var playerNameOut by remember { mutableStateOf(playerName) }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = playerNameOut, onValueChange = {playerNameOut = it},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 35.sp),

            modifier = Modifier.size(250.dp, 300.dp)) /* make it adapt to screen size */

        Button(onClick = { player.playerName = playerNameOut; onNextButtonClicked(player) },
            modifier = Modifier.size(200.dp).aspectRatio(2f)) {
            Text(text = "Enter", textAlign = TextAlign.Center, fontSize = 25.sp)
        }
    }
}

/*.size((LocalConfiguration.current.screenHeightDp * 0.15f).dp) */