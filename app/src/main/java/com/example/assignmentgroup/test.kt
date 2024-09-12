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


var avatarImages23 = listOf(
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
fun test(){ /* dunno if any is an abuse see if theres better ways later on */
    println("asdsasad")
    var gameState: MutableList<MutableList<MutableList<Int>>> = mutableListOf<MutableList<MutableList<Int>>>()/* standard board 7 by 6 */
    val row: Int = 6
    val column: Int = 5
    val initState: Int = -1
    var index: Int = 0
    for(i in 0..row -1 ){
        val rowList = mutableListOf<MutableList<Int>>()
        for(j in 0..column -1)
        {
            rowList.add(mutableListOf(index, initState))
            index += 1
        }
        gameState.add(rowList)
    }
//
    println(gameState)
    LazyVerticalGrid(columns = GridCells.Fixed(column)) {
        for(k in 0.. gameState.count()-1){
            for(l in 0..gameState[0].count()-1)
            {
                items(1) { element ->
                    cardDefaultRenderT(gameState[k][l][0], onClick = { })
                }
            }
        }
    }


    /*LazyVerticalGrid(columns = GridCells.Fixed(column)) {
        for(k in 0.. row-1){
            items(list[k]) { element ->
                cardRedRender(element = element)
            }
        }
    }*/
}


@Composable
fun cardDefaultRenderT(element: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)) {
        Text(
            text = element.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}
