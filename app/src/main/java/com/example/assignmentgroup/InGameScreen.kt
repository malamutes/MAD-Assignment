package com.example.assignmentgroup

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
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
fun inGameScreenPlayer(isPlayerOne: Boolean, gameState: MutableList<MutableList<MutableList<Int>>>,
                 isGameOver: Boolean,
                 onNextButtonClicked: (List<Any>) -> Unit){ /* dunno if any is an abuse see if theres better ways later on */

    var gameStateOut = gameState
    var isGameOverOut = isGameOver

    /* can  be initial render i guess prob shit way to do it will ened to find better solution*/
    LazyVerticalGrid(columns = GridCells.Fixed(gameState[0].count()), modifier = Modifier.height(1.dp)
    /*tf this fixes it but doesnt even do anything*/) {
        for(i in 0..gameState.count()-1)
        {
            for(j in 0..gameState[0].count()-1)
            {
                if(gameState[i][j][1] == 1)
                {
                    items(1) { element ->
                        cardRedRender(element = gameState[i][j][0], onClick = {onNextButtonClicked(listOf(gameStateOut, isGameOver)) })
                    }
                }
                else if(gameState[i][j][1] == 0)
                {
                    items(1) { element ->
                        cardDefaultRender(element = gameState[i][j][0], onClick = {
                            if(isPlayerOne == true)
                            {
                            gameStateOut[i][j][1] = 1
                                if(i - 1 > -1)
                                {
                                    gameStateOut[i - 1][j][1] = 0
                                }
                            onNextButtonClicked(listOf(gameStateOut, isGameOver))
                            }
                            else if(isPlayerOne == false)
                            {
                            gameStateOut[i][j][1] = 2
                                if(i - 1 > -1)
                                {
                                    gameStateOut[i - 1][j][1] = 0
                                }
                            onNextButtonClicked(listOf(gameStateOut, isGameOver))
                            }
                        })
                    }
                }
                else if(gameState[i][j][1] == 2)
                {
                    items(1) { element ->
                        cardBlueRender(element = gameState[i][j][0], onClick = {onNextButtonClicked(listOf(gameStateOut, isGameOver)) })
                    }
                }
                else if(gameState[i][j][1] == -1)
                {
                    items(1) { element ->
                        cardMagentaRender(element = gameState[i][j][0], onClick = {})
                    }
                }
            }
        }

    }
}

@Composable
fun inGameScreenAI(isPlayerOne: Boolean, gameState: MutableList<MutableList<MutableList<Int>>>,
                       isGameOver: Boolean, freeGrids: MutableList<Int>,
                       onNextButtonClicked: (List<Any>) -> Unit){ /* dunno if any is an abuse see if theres better ways later on */

    var gameStateOut = gameState
    var isGameOverOut = isGameOver

    /* can  be initial render i guess prob shit way to do it will ened to find better solution*/
    LazyVerticalGrid(columns = GridCells.Fixed(gameState[0].count()), modifier = Modifier.height(1.dp)
        /*tf this fixes it but doesnt even do anything*/) {
        for(i in 0..gameState.count()-1)
        {
            for(j in 0..gameState[0].count()-1)
            {
                if(gameState[i][j][1] == 1)
                {
                    items(1) { element ->
                        cardRedRender(element = gameState[i][j][0], onClick = {gameStateOut[i][j][1] = 1; onNextButtonClicked(listOf(gameStateOut, isGameOver)) })
                    }
                }
                else if(gameState[i][j][1] == 0)
                {

                    items(1) { element ->
                        cardDefaultRender(element = gameState[i][j][0], onClick = {
                            if(isPlayerOne == true)
                            {
                                gameStateOut[i][j][1] = 1;
                                gameStateOut[i - 1][j][1] = 0;
                                freeGrids.remove(gameStateOut[i][j][0])
                                onNextButtonClicked(listOf(gameStateOut, isGameOver, freeGrids))
                            }
                            else if(isPlayerOne == false)
                            {
                                var pick = freeGrids.random()
                                gameStateOut[(pick / gameState.count()).toInt()][pick % gameState.count()][1] = 2;
                                gameStateOut[i - 1][j][1] = 0;
                                freeGrids.remove(gameStateOut[i][j][0]);
                                onNextButtonClicked(listOf(gameStateOut, isGameOver, freeGrids))
                            }
                        })
                    }
                }
                else if(gameState[i][j][1] == 2)
                {
                    items(1) { element ->
                        cardBlueRender(element = gameState[i][j][0], onClick = {gameStateOut[i][j][1] = 1; onNextButtonClicked(listOf(gameStateOut, isGameOver)) })
                    }
                }
                else if(gameState[i][j][1] == -1)
                {
                    items(1) { element ->
                        cardMagentaRender(element = gameState[i][j][0], onClick = {})
                    }
                }
            }
        }

    }
}

@Composable
fun cardDefaultRender(element: Int, onClick: () -> Unit){
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
        modifier = Modifier
            .aspectRatio(1f)
            .size(50.dp)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

@Composable
fun cardRedRender(element: Int, onClick: () -> Unit){
    Button(onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}

@Composable
fun cardBlueRender(element: Int, onClick: () -> Unit){
    Button(onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}


@Composable
fun cardMagentaRender(element: Int, onClick: () -> Unit){
    Button(onClick = { },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)) {
        Text(text = element.toString(), fontSize = 1.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
    }
}


