package com.example.woltapplication.screens

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.woltapplication.persistence.MainViewModel
import com.example.woltapplication.R
import com.example.woltapplication.data.RestaurantData
import com.example.woltapplication.data.Venue

@Composable
fun RestaurantScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MainViewModel.UiState.Loading -> {
            Text("Loading...", style = MaterialTheme.typography.bodyMedium)
        }

        is MainViewModel.UiState.Success -> {
            val data = (uiState as MainViewModel.UiState.Success).data
            RestaurantList(data)
        }

        is MainViewModel.UiState.LoadingWithData -> {
            val data = (uiState as MainViewModel.UiState.LoadingWithData).data
            // Show data with a "Loading" indicator
            Box {
                RestaurantList(data)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MainViewModel.UiState.Error -> {
            val message = (uiState as MainViewModel.UiState.Error).message
            ErrorState(message = "Please check your internet and try again!")
        }
    }
}

@Composable
fun RestaurantList(data: RestaurantData) {
    val items = data.sections[1].items;
    var itemsToDisplay = items.size;
    if (itemsToDisplay > 15)
        itemsToDisplay = 15;
    val lastIndex = itemsToDisplay - 1
    val listState = rememberLazyListState()


    LazyColumn(state = listState) {
        // Add a single item
        item {
            Text(text = "First item", style = MaterialTheme.typography.labelMedium)
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
            }
            AddHorizontalSpace(12.dp)
            Box(modifier = Modifier.weight(0.1f), Alignment.Center) {
                if (restaurantVenue != null) {
                    var checked by remember { mutableStateOf(restaurantVenue.isFavourite) }
                    IconToggleButton(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp),
                        checked = checked,
                        onCheckedChange = { checked = it }) {
                        if (checked) {
                            restaurantVenue.isFavourite = true;
                            Icon(
                                Icons.Filled.Favorite, contentDescription = "add to favourites icon"
                            )
                        } else {
                            restaurantVenue.isFavourite = false;
                            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "add to favourites icon")
                        }
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