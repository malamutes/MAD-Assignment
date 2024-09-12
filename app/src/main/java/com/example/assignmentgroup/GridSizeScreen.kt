package com.example.assignmentgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GridSizeScreen(gridSizeOptions: List<Pair<String, Int>>,
                   onNextButtonClicked: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        gridSizeOptions.forEach {
            options -> SelectGridSizeButton(
                gridSizeOption = options.first,
                onClick = {
                    onNextButtonClicked(options.second)
                })
        }
    }
}

@Composable
fun SelectGridSizeButton(gridSizeOption: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .padding(25.dp)
    ) {
        Text(
            text = gridSizeOption,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}