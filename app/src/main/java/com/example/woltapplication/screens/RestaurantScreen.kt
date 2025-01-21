package com.example.woltapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.woltapplication.R
import com.example.woltapplication.data.RestaurantData
import com.example.woltapplication.data.Venue
import com.example.woltapplication.persistence.MainViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()
    // to remember the scroll state of the list for new data loading
    val listState = rememberLazyListState()

    val appBarTitle = when (uiState) {
        is MainViewModel.UiState.Success -> "Restaurants nearby you"
        else -> "Finding Restaurants for you..."
    }


    when (uiState) {
        is MainViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(180.dp),   //crops the image to circle shape
                        painter = rememberDrawablePainter(
                            drawable = getDrawable(
                                LocalContext.current,
                                R.drawable.restaurant_marker
                            )
                        ),
                        contentDescription = "Loading animation",
                        contentScale = ContentScale.FillWidth,
                    )
                    AddVerticalSpace(4.dp)
                    Text(
                        "Finding Restaurants for you...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        is MainViewModel.UiState.Success -> {
            val data = (uiState as MainViewModel.UiState.Success).data
            RestaurantList(data, mainViewModel, appBarTitle, listState)
        }

        is MainViewModel.UiState.LoadingWithData -> {
            val data = (uiState as MainViewModel.UiState.LoadingWithData).data
            // Show existing data with a "Loading" indicator
            Box {
                RestaurantList(data, mainViewModel, appBarTitle, listState)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MainViewModel.UiState.Error -> {
            var message = (uiState as MainViewModel.UiState.Error).message
            if (message.contains("Unable to"))
                message = "Please check your internet and try again!"
            ErrorState(message = message)
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.getFavouriteVenuesIDs()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantList(
    data: RestaurantData,
    mainViewModel: MainViewModel,
    appBarTitle: String,
    listState: LazyListState
) {
    val items = data.sections[1].items;
    var itemsToDisplay = items.size;
    if (itemsToDisplay > 15)
        itemsToDisplay = 15;
    val lastIndex = itemsToDisplay - 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        appBarTitle,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Blue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_gps), // Menu icon
                            contentDescription = "Menu Icon",
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = innerPadding, // to avoid content overlapping with the App Bar
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemsToDisplay) { index ->
                val showDivider = (index != lastIndex)
                if (items[index].venue != null) {

                    VenueCardView(
                        items[index].venue,
                        items[index].image?.url,
                        showDivider,
                        mainViewModel
                    )
                }
            }
        }
        //to save the list scroll state for next data fetch
        LaunchedEffect(data) {
            if (listState.firstVisibleItemIndex > 0) {
                listState.scrollToItem(
                    listState.firstVisibleItemIndex,
                    listState.firstVisibleItemScrollOffset
                )
            }
        }
    }
}

@Composable
fun VenueCardView(
    restaurantVenue: Venue?,
    imageUrl: String?,
    showDivider: Boolean,
    mainViewModel: MainViewModel
) {
    val imageDimension = 96.dp
    val horizontalSpacing = 16.dp

    val favouriteVenuesIds by mainViewModel.favouriteVenuesIds.collectAsState()
    if (restaurantVenue != null) {
        if (favouriteVenuesIds.contains(restaurantVenue.id))
            restaurantVenue.isFavourite = true;
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
                        placeholder = painterResource(id = R.drawable.ic_venue_placeholder),
                        error = painterResource(id = R.drawable.ic_venue_placeholder),
                        fallback = painterResource(id = R.drawable.ic_venue_placeholder),
                    )
                }
                AddHorizontalSpace(horizontalSpacing)
                Column(modifier = Modifier.weight(0.7f)) {
                    restaurantVenue.name?.let {
                        Text(text = it, style = MaterialTheme.typography.titleMedium)
                    }
                    AddVerticalSpace(8.dp)
                    restaurantVenue.shortDescription?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                AddHorizontalSpace(12.dp)
                Box(modifier = Modifier.weight(0.1f), Alignment.Center) {
                    var checked by remember { mutableStateOf(restaurantVenue.isFavourite) }
                    IconToggleButton(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }) {
                        if (checked) {
                            restaurantVenue.isFavourite = true;
                            mainViewModel.insertVenue(restaurantVenue)
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "add to favourites icon"
                            )
                        } else {
                            restaurantVenue.isFavourite = false;
                            mainViewModel.deletedVenue(restaurantVenue)
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = "add to favourites icon"
                            )
                        }
                    }
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
}

@Composable
fun AddVerticalSpace(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun AddHorizontalSpace(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun ErrorState(modifier: Modifier = Modifier, message: String) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_internet),
                contentDescription = "No internet connection image"
            )
            AddVerticalSpace(height = 8.dp)
            Text(text = message, color = Color.Red, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}