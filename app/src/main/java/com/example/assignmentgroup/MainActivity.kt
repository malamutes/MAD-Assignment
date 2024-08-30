package com.example.assignmentgroup

import androidx.activity.enableEdgeToEdge
import UI.`material-theme`.ui.theme.AppTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable

/* my own functions and files */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(darkTheme = true, dynamicColor = false) {
                GameApp()
            }
        }
    }
}

