package com.example.assignmentgroup

import androidx.compose.ui.graphics.Color
import com.example.recyclerviewcompose.R

object DataSource {
    val gridSizeOptions = listOf<Pair<String, Int>>(
        Pair("Standard (7X6)", 0),
        Pair("Small (6X5)", 1),
        Pair("Large (8X7)", 2)
    )

    val colorOptions = listOf<Pair<Color, String>>(
        Pair(Color(0xFFFFA500), "Orange"),
        Pair(Color(0xFFFFFFFF), "White"),
        Pair(Color(0xFF702963), "Purple"),
        Pair(Color(0xFFBA1A1A), "Red"),
        Pair(Color(0xFFFFDAD6), "Pale Pink"),
        Pair(Color(0xFFF9DB22), "Yellow"),
        Pair(Color(0xFF000000), "Black"),
        Pair(Color(0xFF298b28), "Green"),
        Pair(Color.Blue, "Blue")
    )

    val playerOrAIOptions = listOf<Pair<String, Boolean>>(
        Pair("VS PLAYER", true),
        Pair("VS AI", false),/* referenced by output of playervsai screen output to set boolean for ai or player flag*/
    )

    var avatarImages = listOf(
        R.drawable.vik,
        R.drawable.ekko,
        R.drawable.jayce,
        R.drawable.jinx,
        R.drawable.silco,
        R.drawable.lucy,
    )

    val playAgain = listOf<Pair<String, Boolean>>(
        Pair("MAIN MENU", false),
        Pair("PLAY AGAIN", true)
    )
}