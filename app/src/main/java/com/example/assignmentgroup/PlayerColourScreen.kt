package com.example.assignmentgroup

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerColourScreen(colorOption: List<Pair<Color, String>>,
                       player: Player,
                       onNextButtonClicked: (Player) -> Unit) {
//    LazyRow(modifier = Modifier.fillMaxHeight()) {
//        items(colorOption) {
//            colors -> DiscColourCard(colorOption = colors,
//            onClick = { player.playerColor = colors; onNextButtonClicked(player) })
//        }
//    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .height(1.dp)
    ) {
        items(colorOption) {
            colors -> DiscColourCard(colorOption = colors,
            onClick = {
                player.playerColor = colors;
                onNextButtonClicked(player)
            })
        }
    }
}


@Composable
fun DiscColourCard(colorOption: Pair<Color, String>, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = colorOption.first),
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .aspectRatio(1f)
            .padding(15.dp)
    ) {
        Text(
            text = colorOption.second,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}