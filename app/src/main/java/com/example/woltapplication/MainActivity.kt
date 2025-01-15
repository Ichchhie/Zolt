package com.example.woltapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.example.woltapplication.api.ApiService
import com.example.woltapplication.api.RestaurantData
import com.example.woltapplication.api.Venue
import com.example.woltapplication.ui.theme.WoltApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val locationCoordinates = listOf(
        Pair(60.169418, 24.931618),
        Pair(60.169818, 24.932906),
        Pair(60.170005, 24.935105),
        Pair(60.169108, 24.936210),
        Pair(60.168355, 24.934869),
        Pair(60.167560, 24.932562),
        Pair(60.168254, 24.931532),
        Pair(60.169012, 24.930341),
        Pair(60.170085, 24.929569)
    )
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val statusBarColor = ContextCompat.getColor(applicationContext, R.color.purple_700)
        val navigationBarColor = ContextCompat.getColor(applicationContext, R.color.black)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(statusBarColor),
            navigationBarStyle = SystemBarStyle.dark(navigationBarColor)
        )
        setContent {
            val apiService = ApiService()
            var apiResponse by remember { mutableStateOf<RestaurantData?>(null) }
            var errorMessage by remember { mutableStateOf("") }
            var currentLocation by remember { mutableStateOf(locationCoordinates[currentIndex]) }

            // Launch coroutine to update location every 10 seconds
            LaunchedEffect(Unit) {
                while (true) {
                    try {
                        // Update the location coordinates
                        currentLocation = locationCoordinates[currentIndex]

                        // Fetch data with the new location
                        apiResponse = apiService.getData(currentLocation.first, currentLocation.second)

                        // Log the current location being used for API
                        Log.d("Location Update", "Fetching data for: $currentLocation")

                        // Update the index to the next location, looping back to the first if needed
                        currentIndex = (currentIndex + 1) % locationCoordinates.size
                        Log.d("Location Update", "Current Index: $currentIndex")

                    } catch (e: Exception) {
                        Log.d("Location Update", "Error fetching data: ${e.message}")
                        errorMessage = "Failed to fetch data: ${e.message}"
                    }

                    // Delay for 10 seconds before updating again
                    delay(10000)  // 10 seconds
                }
            }

            WoltApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    Log.d("Location Update", "Greeting:called ")
    val items = restaurantData.sections[1].items;
    var itemsToDisplay = items.size;
    if (itemsToDisplay > 15)
        itemsToDisplay = 15;
    val lastIndex = itemsToDisplay - 1
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }
        items(itemsToDisplay) { index ->
            val showDivider = (index != lastIndex)
            VenueCardView(items[index].venue, items[index].image?.url, showDivider)
        }
    }
}

@Composable
fun VenueCardView(restaurantVenue: Venue?, imageUrl: String?, showDivider: Boolean) {
    val imageDimension = 96.dp
    val horizontalSpacing = 16.dp
    Column {
        Row(
            modifier = Modifier.padding(horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.3f)) {
                AsyncImage(
                    model = imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Translated description of what the image contains",
                    modifier = Modifier
                        .height(imageDimension)
                        .width(imageDimension)
                        .clip(RoundedCornerShape(size = 16.dp)),
                    placeholder = painterResource(id = R.drawable.ic_favorite_border_icon),
                    error = painterResource(id = R.drawable.ic_favorite_border_icon),
                    fallback = painterResource(id = R.drawable.ic_favorite_border_icon),
                )
            }
            AddHorizontalSpace(horizontalSpacing)
            Column(modifier = Modifier.weight(0.7f)) {
                if (restaurantVenue != null) {
                    restaurantVenue.name?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    AddVerticalSpace(8.dp)
                    restaurantVenue.shortDescription?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 18.sp  // Reduced line spacing
                            )
                        )
                    }
                }
            }
            AddHorizontalSpace(12.dp)
            Box(modifier = Modifier.weight(0.1f), Alignment.Center) {
                Image(
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp),
                    painter = painterResource(id = R.drawable.ic_favorite_border_icon),
                    contentDescription = "favourite icon"
                )
            }
        }
        if (showDivider) {
            Row(modifier = Modifier.padding(horizontal = horizontalSpacing)) {
                AddHorizontalSpace(imageDimension + horizontalSpacing)
                HorizontalDivider(color = Color.LightGray, thickness = 1.5.dp)
            }
        }
    }
}

@Composable
fun AddVerticalSpace(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun AddHorizontalSpace(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WoltApplicationTheme {
        Greeting("Android", restaurantData = RestaurantData())
    }
}