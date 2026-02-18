package com.propertyfinder.shopr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.propertyfinder.shopr.ui.GroceryListScreen
import com.propertyfinder.shopr.ui.theme.ShoprTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoprTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GroceryListScreen()
                }
            }
        }
    }
}
