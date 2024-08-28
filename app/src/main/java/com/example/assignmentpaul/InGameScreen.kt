package com.example.assignmentpaul

import android.widget.Button
import android.widget.Toast
import androidx.collection.ObjectList
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recyclerviewcompose.R
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import java.util.Objects
import kotlin.random.Random


var avatarImages2 = listOf(
    R.drawable.vik,
    R.drawable.ekko,
    R.drawable.jayce,
    R.drawable.jinx,
    R.drawable.silco,
    R.drawable.lucy,
)


/* 2d matrix with list in each entry for index and state, so 3d matrix*/
fun initGridState(gridSizeOptions: Int,
                  gameState: MutableList<MutableList<MutableList<Int>>>): MutableList<MutableList<MutableList<Int>>>{
    if(gridSizeOptions == 0){ /* standard board 7 by 6 */
        val row: Int = 7
        val column: Int = 6
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
    }
    if(gridSizeOptions == 0){ /* small board 6 by 5 */
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
    }
    else if(gridSizeOptions == 1){ /* large board 8 by 7 */
        val row: Int = 8
        val column: Int = 7
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
    }
    return gameState
}

@Composable
fun inGameScreen(gridSizeOptions: Int, isGridMade: Boolean, gameState: MutableList<MutableList<MutableList<Int>>>,
                 onNextButtonClicked: () -> Unit){
    if(isGridMade == false)
    {
        initGridState(gridSizeOptions, gameState)
    }

    Button(onClick = {},
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .size(
                width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
            )
            .padding(1.dp)) {
        Text(text = "Confirm", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
    /* can  be initial render i guess prob shit way to do it will ened to find better solution*/
//    LazyVerticalGrid(columns = GridCells.Fixed(gameState[0]. /* check column try last index */)) {
//        for(k in 0.. row - 1){
//            items(list[k]) { element ->
//                if(element[1] == 1){
//                    cardDefaultRender(element = element, onClick = { (onNextButtonClicked(element[0]).toString()
//                            + onNextButtonClicked(element[1]).toString()) })
//                }
//                else if(element[1] == -1)
//                {
//                    cardPlayerRender(element = element, onClick = { (onNextButtonClicked(element[0]).toString()
//                            + onNextButtonClicked(element[1]).toString()) })
//                }
//            }
//        }
//    }


    /*LazyVerticalGrid(columns = GridCells.Fixed(column)) {
        for(k in 0.. row-1){
            items(list[k]) { element ->
                cardRedRender(element = element)
            }
        }
    }*/
}


@Composable
fun cardDefaultRender(element: MutableList<Int>, onClick: () -> Unit){
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

