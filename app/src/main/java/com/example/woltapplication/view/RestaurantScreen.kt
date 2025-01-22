package com.example.woltapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()
    // to remember the scroll state of the list for new data loading
    val listState = rememberLazyListState()
    // to show while fetching venues for new location
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val appBarTitle = when (uiState) {
//        is MainViewModel.UiState.Success -> stringResource(R.string.nearby_restaurants_label)
        is MainViewModel.UiState.Loading -> stringResource(R.string.finding_restaurants_label)
        else -> stringResource(R.string.your_current_location_label)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.ic_gps),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        AddHorizontalSpace(16.dp)
                        Text(
                            appBarTitle,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.semantics { contentDescription = appBarTitle }
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .semantics { contentDescription = "" }
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, // to put space between text and progress bar
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.loading_new_restaurants_label))
                        // Progress bar on the right side
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp), // Size of the progress indicator
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is MainViewModel.UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.semantics(mergeDescendants = true) {}) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(180.dp),
                                painter = rememberDrawablePainter(
                                    drawable = getDrawable(
                                        context,
                                        R.drawable.restaurant_marker
                                    )
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                            )
                            AddVerticalSpace(8.dp)
                            Text(
                                stringResource(R.string.finding_restaurants_label),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                is MainViewModel.UiState.Success -> {
                    val data = (uiState as MainViewModel.UiState.Success).data
                    RestaurantList(data, mainViewModel, listState)
                    // Dismiss the Snackbar once the data are loaded
                    LaunchedEffect(Unit) {
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }

                is MainViewModel.UiState.LoadingWithData -> {
                    val data = (uiState as MainViewModel.UiState.LoadingWithData).data
                    // Show existing data with a "Loading" indicator
                    Box {
                        RestaurantList(
                            data,
                            mainViewModel,
                            listState
                        )

                        // Show Snackbar for new data loading
                        LaunchedEffect(Unit) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.loading_new_data_text),
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }

                is MainViewModel.UiState.Error -> {
                    var message = (uiState as MainViewModel.UiState.Error).message
                    if (message.contains("Unable to"))
                        message = stringResource(R.string.no_internet_connection)
                    ErrorState(message = message)
                }
            }
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
    listState: LazyListState
) {
    val items = data.sections[1].items
    var itemsToDisplay = items.size
    if (itemsToDisplay > 15)
        itemsToDisplay = 15
    val lastIndex = itemsToDisplay - 1
    val horizontalSpacing = 16.dp

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalSpacing)
    ) {
        item {
            Text(
                text = stringResource(R.string.nearby_restaurants_label),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
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
            restaurantVenue.isFavourite = true

        Row(
            modifier = Modifier
                .padding(vertical = horizontalSpacing)
                .semantics(mergeDescendants = true) {},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.3f)) {
                AsyncImage(
                    model = imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
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
            Column(modifier = Modifier.weight(0.6f)) {
                restaurantVenue.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.semantics { heading() })
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
            Box(modifier = Modifier
                .weight(0.2f)
                .width(48.dp)
                .height(48.dp), Alignment.CenterEnd) {
                var checked by remember { mutableStateOf(restaurantVenue.isFavourite) }
                val stateFavorite = stringResource(R.string.favourited_state)
                val stateNotFavorite = stringResource(R.string.not_favorited_state)
                IconToggleButton(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    modifier = Modifier.semantics {
                        stateDescription = if (checked) stateFavorite else stateNotFavorite
                    }
                ) {
                    if (checked) {
                        restaurantVenue.isFavourite = true
                        mainViewModel.insertVenue(restaurantVenue)
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = stringResource(R.string.add_to_favourites_icon_label)
                        )
                    } else {
                        restaurantVenue.isFavourite = false
                        mainViewModel.deletedVenue(restaurantVenue)
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.remove_from_favourites_icon_label),
//                            modifier = Modifier.clearAndSetSemantics { } // Avoid redundancy of explaining the icon for each list item
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.semantics(mergeDescendants = true) {}) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_internet),
                contentDescription = null
            )
            AddVerticalSpace(height = 8.dp)
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}