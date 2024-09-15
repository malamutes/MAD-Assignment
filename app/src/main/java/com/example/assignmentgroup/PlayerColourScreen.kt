package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp

@Composable
fun PlayerColourScreen(colorOption: List<Pair<Color, String>>, player: Player, heading: String, onNextButtonClicked: (Player) -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column {
            Text (
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
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
            ) {
                items(colorOption) { colors ->
                    DiscColourCard(
                        colorOption = colors,
                        size = LocalConfiguration.current.screenWidthDp * 0.25f,
                        onClick = {
                            player.playerColor = colors;
                            onNextButtonClicked(player)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DiscColourCard(colorOption: Pair<Color, String>, size: Float, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = colorOption.first),
        modifier = Modifier
            .size(
                width = size.dp,
                height = size.dp
            )
            .padding(15.dp, 15.dp)
            .aspectRatio(1f)
    ) {
        if (colorOption.second == "Purple" || colorOption.second == "Black") {
            Text(
                text = colorOption.second,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.LightGray
            )
        }
        else {
            Text(
                text = colorOption.second,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.Black
            )
        }
    }
}

@Composable
fun HorizontalPlayerColourScreen(colorOption: List<Pair<Color, String>>, player: Player, heading: String, onNextButtonClicked: (Player) -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column {
            Text (
                text = heading,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 10.dp, 0.dp, 5.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .weight(1f)
            ) {
                items(colorOption) { colors -> DiscColourCard(
                    colorOption = colors,
                    size = kotlin.math.min(LocalConfiguration.current.screenWidthDp * 0.15f, LocalConfiguration.current.screenHeightDp * 0.3f),
                    onClick = {
                        player.playerColor = colors
                        onNextButtonClicked(player)
                    }
                ) }
            }
        }
    }

}