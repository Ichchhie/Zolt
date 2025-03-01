## Zolt App
This app is developed to learn and explore various modern Android development concepts using Kotlin. The application is built using Jetpack Compose for the UI, Room for local database management, Hilt for dependency injection, and Ktor for networking.

The application is inspired by the Wolt app, with the goal of implementing key features such as restaurant listing, adding restaurants to favourites, and handling different app states like loading and no internet connection. By using these modern Android libraries, this project aims to demonstrate efficient and scalable approaches to building Android apps.

## App screenshots
<div style="display: flex; justify-content: space-around;">
  <img src="screenshots/Screenshot_loading.png" width="30%" />
  <img src="screenshots/Screenshot_restaurants_list.png" width="30%" />
  <img src="screenshots/screenshot_no_internet.png" width="30%" />
</div>

## Core Features

1. **Dynamic Venue List**:
    - Displays up to **15 venues** near the user's current location.
    - Updates every **10 seconds** as the user's location changes.
    - Loops through the provided location list.

2. **Favorites Management**:
    - Users can toggle venues as **favorites** using a heart icon.
    - Favorite states are persisted across app restarts.

3. **Handling of no internet connection**
4. **API Integration**:
    - Data fetched from the Wolt API:  
      `https://restaurant-api.wolt.com/v1/pages/restaurants?lat=60.170187&lon=24.930599`
    - Utilizes key fields like venue name, description, image, and id.

## Tech Stack

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Networking**: Ktor
- **Local Database**: Room
- **Dependency Injection**: Hilt
- **State Management**: StateFlow 

