package com.example.woltapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.example.woltapplication.screens.RestaurantScreen
import com.example.woltapplication.ui.theme.WoltApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val statusBarColor = ContextCompat.getColor(applicationContext, R.color.purple_700)
        val navigationBarColor = ContextCompat.getColor(applicationContext, R.color.black)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(statusBarColor),
            navigationBarStyle = SystemBarStyle.dark(navigationBarColor)
        )
        setContent {
            WoltApplicationTheme {
                RestaurantScreen()
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WoltApplicationTheme {
//        Greeting("Android", restaurantData = RestaurantData())
//    }
//}