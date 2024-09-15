package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import com.example.ui.theme.displayFontFamily


@Composable
fun PlayerOrAIScreen(playerOrAiOption: List<Pair<String, Boolean>>, onNextButtonClicked: (Boolean) -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Choose Game Mode",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 0.dp, 0.dp, 20.dp)
            )

            playerOrAiOption.forEach() { options ->
                PlayerOrAIButton(
                    playerVsAiOption = options.first,
                    size = LocalConfiguration.current.screenHeightDp * 0.25f,
                    onClick = { onNextButtonClicked(options.second) }
                )
            }
        }
    }
}

@Composable
fun PlayerOrAIButton(playerVsAiOption: String, size: Float, onClick: () -> Unit) {
    var fontSize = 25
    if (playerVsAiOption == DataSource.playerOrAIOptions[0].first)
        fontSize = 15

    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(
                width = size.dp,
                height = size.dp
            )
            .padding(25.dp)
    ) {
        Text(
            text = playerVsAiOption,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun HorizontalPlayerOrAIScreen(playerOrAiOption: List<Pair<String, Boolean>>, onNextButtonClicked: (Boolean) -> Unit) {
    Surface (
        modifier = Modifier
            .fillMaxSize(1f),
        color = Color(0xFF101111)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Choose Game Mode",
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
            )

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                playerOrAiOption.forEach() { options ->
                    PlayerOrAIButton(
                        playerVsAiOption = options.first,
                        size = (LocalConfiguration.current.screenHeightDp * 0.5f),
                        onClick = { onNextButtonClicked(options.second) }
                    )
                }
            }
        }
    }
}


