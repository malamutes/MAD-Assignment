package com.example.assignmentgroup

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerNameScreen(playerName: String, player: Player, onNextButtonClicked: (Player) -> Unit){
    var playerNameOut by remember { mutableStateOf(playerName) }
    Column {
        TextField(value = playerNameOut, onValueChange = {playerNameOut = it})
        Button(onClick = { player.playerName = playerNameOut; onNextButtonClicked(player) }) {
            Text(text = "Enter")
        }
    }
}


/* name doesnt work w/e cant save it for now */