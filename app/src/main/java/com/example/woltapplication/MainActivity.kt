package com.example.woltapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.woltapplication.api.ApiService
import com.example.woltapplication.api.RestaurantData
import com.example.woltapplication.api.Venue
import com.example.woltapplication.ui.theme.WoltApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Log.d("apple", "reached first ")
            val apiService = ApiService()
            val coroutineScope = rememberCoroutineScope()
            var apiResponse by remember { mutableStateOf<RestaurantData?>(null) }
            var errorMessage by remember { mutableStateOf("") }

            WoltApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            try {
                                Log.d("apple", "reached couroutines ")
                                apiResponse = apiService.getData()
                            } catch (e: Exception) {
                                Log.d("apple", "error couroutines ")
                                errorMessage = "Failed to fetch data: ${e.message}"
                            }
                        }
                    }

                    apiResponse?.let {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding),
                            restaurantData = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, restaurantData: RestaurantData) {
    Log.d("apple", "hya ")
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }
        Log.d("apple", "Greeting: " + restaurantData)        // Add 5 items
        items(restaurantData.sections[1].items.size) { index ->
            VenueCardView(restaurantData.sections[1].items[index].venue)
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

@Composable
fun VenueCardView(restaurantVenue: Venue?) {
    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = "https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg",
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(size = 16.dp)),
            placeholder = painterResource(id = R.drawable.ic_favorite_border_icon),
            error = painterResource(id = R.drawable.ic_favorite_border_icon),
            fallback = painterResource(id = R.drawable.ic_favorite_border_icon),

            )
        Spacer(modifier = Modifier.width(12.dp))
        Column() {
            if (restaurantVenue != null) {
                restaurantVenue.name?.let { Text(text = it) }
                restaurantVenue.shortDescription?.let { Text(text = it) }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            modifier = Modifier
                .width(45.dp)
                .height(45.dp),
            painter = painterResource(id = R.drawable.ic_favorite_border_icon),
            contentDescription = "favourite icon"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WoltApplicationTheme {
        Greeting("Android", restaurantData = RestaurantData())
    }
}