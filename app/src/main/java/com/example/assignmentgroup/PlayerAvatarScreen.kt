package com.example.assignmentgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerAvatarScreen(avatar: List<Int>, player: Player, heading: String, onNextButtonClicked: (Player) -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column {
            Text(
                text = heading,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 60.dp, 0.dp, 5.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                items(avatar) { avatar ->
                    PlayerAvatar(
                        avatar = avatar,
                        onClick = {
                            player.playerAvatar = avatar;
                            onNextButtonClicked(player)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerAvatar(avatar: Int, onClick: () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 15.dp, y = 25.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.20f).dp)
                .clip(CircleShape)
                .clickable { onClick() }
        )
        /*Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier
                .size(
                    width = (LocalConfiguration.current.screenHeightDp * 0.105f).dp,
                    height = (LocalConfiguration.current.screenHeightDp * 0.105f).dp
                )
                .padding(25.dp)
        ) {
            Text(
                text = "✓",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }*/
    }
}