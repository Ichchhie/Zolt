package com.example.woltapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.woltapplication.screens.RestaurantScreen
import com.example.woltapplication.ui.theme.WoltApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //from Android 12+ devices use the system's Material colors (based on the wallpaper or theme) to generate a color scheme dynamically.
            WoltApplicationTheme(dynamicColor = false) {
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