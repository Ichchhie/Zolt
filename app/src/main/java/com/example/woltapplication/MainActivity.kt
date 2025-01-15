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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.woltapplication.api.ApiService
import com.example.woltapplication.data.RestaurantData
import com.example.woltapplication.data.Venue
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
//            var errorMessage by remember { mutableStateOf("") }
            var currentLocation by remember { mutableStateOf(locationCoordinates[currentIndex]) }

            // Launch coroutine to update location every 10 seconds
//            LaunchedEffect(Unit) {
//                while (true) {
//                    try {
//                        // Update the location coordinates
//                        currentLocation = locationCoordinates[currentIndex]
//
//                        // Fetch data with the new location
//                        apiResponse = apiService.getData(currentLocation.first, currentLocation.second)
//
//                        // Log the current location being used for API
//                        Log.d("Location Update", "Fetching data for: $currentLocation")
//
//                        // Update the index to the next location, looping back to the first if needed
//                        currentIndex = (currentIndex + 1) % locationCoordinates.size
//                        Log.d("Location Update", "Current Index: $currentIndex")
//
//                    } catch (e: Exception) {
//                        Log.d("Location Update", "Error fetching data: ${e.message}")
//                        errorMessage = "Failed to fetch data: ${e.message}"
//                    }
//
//                    // Delay for 10 seconds before updating again
//                    delay(10000)  // 10 seconds
//                }
//            }

//            WoltApplicationTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    apiResponse?.let {
//                        Greeting(
//                            name = "Android",
//                            modifier = Modifier.padding(innerPadding),
//                            restaurantData = it
//                        )
//                    }
//                }
//            }
            val viewModel: MainViewModel = viewModel()

            WoltApplicationTheme {
                // Use viewModels to obtain an instance of MainViewModel
                val mainViewModel: MainViewModel = viewModel()

                // Call RestaurantScreen and pass the ViewModel
                RestaurantScreen(viewModel = mainViewModel)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    // Handle loading, error, and success UI states
//                    val uiState by viewModel.uiState.collectAsState()
//
//                    when (uiState) {
//                        is MainViewModel.UiState.Loading -> {
//                            Text("Loading...")
//                        }
//                        is MainViewModel.UiState.Success -> {
//                            val data = (uiState as MainViewModel.UiState.Success).data
//                            RestaurantList(data)
//                        }
//                        is MainViewModel.UiState.Error -> {
//                            val message = (uiState as MainViewModel.UiState.Error).message
//                            Text("Error: $message")
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WoltApplicationTheme {
//        Greeting("Android", restaurantData = RestaurantData())
//    }
//}