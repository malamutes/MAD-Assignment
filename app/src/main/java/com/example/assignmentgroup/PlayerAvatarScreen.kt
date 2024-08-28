package com.example.assignmentgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerAvatarScreen(avatar: List<Int>, player: Player, onNextButtonClicked: (Player) -> Unit){
    /*LazyVerticalGrid(columns = GridCells.Adaptive(128.dp), modifier = Modifier.fillMaxHeight()) {
        items(avatar) {
                avatar -> PlayerAvatar(avatar)
        }
    }*/
    LazyRow(modifier = Modifier.fillMaxHeight()) {
        items(avatar) {
                avatar -> PlayerAvatar(avatar, onClick = {player.playerAvatar = avatar; onNextButtonClicked(player)})
        }
    }
}

@Composable
fun PlayerAvatar(avatar: Int, onClick: () -> Unit) {
    Column {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.25f).dp)
                .clip(CircleShape)
            /* .clickable {}*/
        )
        Button(onClick = onClick,
            shape = CircleShape,
            modifier = Modifier
                .size(
                    width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                    height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
                )
                .padding(25.dp)) {
            Text(text = "PICK ME!", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
        }
    }


}

/* need to somehow try making images clickable to return integer of it */