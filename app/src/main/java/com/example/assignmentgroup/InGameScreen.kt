package com.example.assignmentgroup

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recyclerviewcompose.R


var avatarImages2 = listOf(
    R.drawable.vik,
    R.drawable.ekko,
    R.drawable.jayce,
    R.drawable.jinx,
    R.drawable.silco,
    R.drawable.lucy,
)


/* 2d matrix with list in each entry for index and state, so 3d matrix*/
//so row, column and then inner most list is < index, state of index>

@Composable
fun inGameScreen(gameState: MutableList<MutableList<MutableList<Int>>>,
                 isGameOver: Boolean,
                 onNextButtonClicked: (List<Any>) -> Unit){ /* dunno if any is an abuse see if theres better ways later on */

    var isGameOverOut = isGameOver
    var gameStateOut = gameState


    /*Button(onClick = {onNextButtonClicked(listOf(gameStateOut, isGameOverOut))},
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .padding(1.dp)) {
        Text(text = "Confirm", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    } */

    /* can  be initial render i guess prob shit way to do it will ened to find better solution*/
//    LazyVerticalGrid(columns = GridCells.Fixed(7 /* getting column of gamestate i think */)) {
//        for(i in 0.. gameState[1].count() - 1) {
//            for (j in 0..gameState[0].count() - 1) {
//                items(gameState[1]) { element ->
//                    cardDefaultRender(element = gameState[i][j][0], onClick = { })
//                }
//            }
//        }
//    }
//
        println(gameState[0].count())


    /*LazyVerticalGrid(columns = GridCells.Fixed(column)) {
        for(k in 0.. row-1){
            items(list[k]) { element ->
                cardRedRender(element = element)
            }
        }
    }*/
}


@Composable
fun cardDefaultRender(element: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

@Composable
fun cardPlayerRender(element: MutableList<Int>, onClick: () -> Unit){
    Button(onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

