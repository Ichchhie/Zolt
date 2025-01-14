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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val statusBarColor = ContextCompat.getColor(applicationContext, R.color.purple_700)
        val navigationBarColor = ContextCompat.getColor(applicationContext, R.color.black)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(statusBarColor), navigationBarStyle = SystemBarStyle.dark(navigationBarColor))
        setContent {
            val apiService = ApiService()
            val coroutineScope = rememberCoroutineScope()
            var apiResponse by remember { mutableStateOf<RestaurantData?>(null) }
            var errorMessage by remember { mutableStateOf("") }

            WoltApplicationTheme {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        try {
                            Log.d("apple", "reached couroutines ")
                            apiResponse = apiService.getData(60.170187, 24.930599)
                        } catch (e: Exception) {
                            Log.d("apple", "error couroutines ")
                            errorMessage = "Failed to fetch data: ${e.message}"
                        }
                    }
                }

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
    val items = restaurantData.sections[1].items;
    var itemsToDisplay = items.size;
    if(itemsToDisplay > 15)
        itemsToDisplay = 15;
    val lastIndex = itemsToDisplay-1
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }
        items(itemsToDisplay) { index ->
            val showDivider = (index != lastIndex)
            VenueCardView(items[index].venue, items[index].image?.url, showDivider)
        }
        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

@Composable
fun VenueCardView(restaurantVenue: Venue?, imageUrl: String?, showDivider: Boolean) {
    val imageDimension = 96.dp
    val horizontalSpacing = 16.dp
    Column {
        Row(modifier = Modifier.padding(horizontalSpacing), verticalAlignment = Alignment.CenterVertically) {
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
        if(showDivider){
            Row(modifier = Modifier.padding(horizontal = horizontalSpacing)) {
                AddHorizontalSpace(imageDimension + horizontalSpacing)
                HorizontalDivider(color = Color.LightGray, thickness = 1.5.dp)
            }
        }
    }
}

@Composable
fun AddVerticalSpace(height: Dp){
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