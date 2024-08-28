package com.example.assignmentpaul

import android.widget.Button
import android.widget.Toast
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
import kotlin.random.Random


@Composable
fun PlayerConfirmScreen(avatar1: Int, name1: String, color1: Color,
                        avatar2: Int, name2: String, color2: Color,
                        vsPlayer: Boolean,
                        onNextButtonClicked: () -> Unit){
    if(vsPlayer == true)
    {
        Column(modifier = Modifier.padding(50.dp)) {
            PlayerConfirm(avatar = avatar1, name = name1, color = color1 ) {}
            PlayerConfirm(avatar = avatar2, name = name2, color = color2) {}
            confirmButton(onClick = {onNextButtonClicked()})
        }

    }
    else if(vsPlayer == false)
    {
        Column(modifier = Modifier.padding(50.dp)) {
            PlayerConfirm(avatar = avatar1, name = name1, color = color1) {}
            confirmButton(onClick = {onNextButtonClicked()})
        }

    }
}

@Composable
fun PlayerConfirm(avatar: Int, name: String, color: Color,  onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .aspectRatio(2f)
        .offset(y = (LocalConfiguration.current.screenHeightDp * 0.025f).dp)
        .padding((LocalConfiguration.current.screenHeightDp * 0.01f).dp)
        .border(
            width = 2.5.dp,
            color = MaterialTheme.colorScheme.scrim,
            shape = MaterialTheme.shapes.extraSmall
        )
        .background(color = Color.Black),
        horizontalArrangement = Arrangement.spacedBy(25.dp)) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.15f).dp)
                .clip(CircleShape)
                .border(BorderStroke(5.dp, color), shape = CircleShape)
        )
        Column {
            Text(text = name, color = color)
            Text(text = color.toString(), color = color)
        }
    }
}

@Composable
fun confirmButton(onClick: () -> Unit){
    Button(onClick = onClick,
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
}
