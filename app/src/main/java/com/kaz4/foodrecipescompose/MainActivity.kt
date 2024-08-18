package com.kaz4.foodrecipescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kaz4.foodrecipescompose.ui.components.toolbar.NavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = { NavigationBar(Modifier) }
            ) { paddingValues ->
                paddingValues.calculateTopPadding()
            }
        }
    }
}
