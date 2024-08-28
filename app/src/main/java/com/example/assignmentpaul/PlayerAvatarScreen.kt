package com.example.assignmentpaul

import android.widget.Button
import android.widget.Toast
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerAvatarScreen(avatar: List<Int>, onNextButtonClicked: (Int) -> Unit){
    /*LazyVerticalGrid(columns = GridCells.Adaptive(128.dp), modifier = Modifier.fillMaxHeight()) {
        items(avatar) {
                avatar -> PlayerAvatar(avatar)
        }
    }*/
    LazyRow(modifier = Modifier.fillMaxHeight()) {
        items(avatar) {
                avatar -> PlayerAvatar(avatar, onClick = {onNextButtonClicked(avatar)})
        }
    }
}

@Composable
fun PlayerAvatar(avatar: Int, onClick: () -> Unit) {
    Column {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = avatar.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 10.dp)
                .size((LocalConfiguration.current.screenHeightDp * 0.25f).dp)
                .clip(CircleShape)
            /* .clickable {}*/
        )
        Button(onClick = onClick,
            shape = CircleShape,
            modifier = Modifier
                .size(
                    width = (LocalConfiguration.current.screenHeightDp * 0.25f).dp,
                    height = (LocalConfiguration.current.screenHeightDp * 0.25f).dp
                )
                .padding(25.dp)) {
            Text(text = "PICK ME!", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
        }
    }


}

/* need to somehow try making images clickable to return integer of it */