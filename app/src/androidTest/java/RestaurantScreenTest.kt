import androidx.compose.ui.test.junit4.createComposeRule
import com.example.woltapplication.view.RestaurantScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@HiltAndroidTest
//class RestaurantScreenTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this) // Sets up Hilt for this test
//
//    @get:Rule
//    val composeTestRule = createComposeRule() // For Compose testing
//
//    @Before
//    fun setup() {
//        hiltRule.inject() // Initializes the dependency graph for Hilt
//    }
//
//    @Test
//    fun testLoadingState() {
//        composeTestRule.setContent {
//            RestaurantScreen() // Hilt provides the ViewModel
//        }
//
//        // Assert the UI based on the state
////        composeTestRule.onNodeWithText("Finding Restaurants").assertIsDisplayed()
//    }
//}


//@HiltAndroidTest
//class RestaurantScreenTest {
//
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    val composeTestRule = createComposeRule()
//
//    private lateinit var fakeViewModel: MainViewModel
//
//    @Before
//    fun setup() {
////        hiltRule.inject()
//        fakeViewModel = MainViewModel()
//    }
//
//    @Test
//    fun testLoadingState() {
//        // Arrange: Set up the fake ViewModel to emit a Loading state
//        fakeViewModel.setUiState(UiState.Loading)
//
//        // Act: Launch the composable with the fake ViewModel
//        composeTestRule.setContent {
//            RestaurantScreen(viewModel = fakeViewModel)
//        }
//
//        // Assert: Verify that the loading state UI is displayed
//        composeTestRule.onNodeWithText("Finding Restaurants").assertIsDisplayed()
//        composeTestRule.onNodeWithContentDescription("loading_restaurants_icon").assertIsDisplayed()
//    }
//
////    @Test
////    fun testSuccessState() {
////        // Arrange: Set up the fake ViewModel to emit a Success state
////        fakeViewModel.setUiState(UiState.Success(listOf("Restaurant A", "Restaurant B")))
////
////        // Act: Launch the composable with the fake ViewModel
////        composeTestRule.setContent {
////            RestaurantScreen(viewModel = fakeViewModel)
////        }
////
////        // Assert: Verify that the restaurant list is displayed and no loading UI is shown
////        composeTestRule.onNodeWithText("Nearby Restaurants").assertIsDisplayed()
////        composeTestRule.onNodeWithText("Finding Restaurants").assertDoesNotExist()
////    }
//
//    @Test
//    fun testErrorState() {
//        // Arrange: Set up the fake ViewModel to emit an Error state
//        fakeViewModel.setUiState(UiState.Error("Something went wrong"))
//
//        // Act: Launch the composable with the fake ViewModel
//        composeTestRule.setContent {
//            RestaurantScreen(viewModel = fakeViewModel)
//        }
//
//        // Assert: Verify that the error message is displayed
//        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
//    }
//}
