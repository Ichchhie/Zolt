# Wolt 2025 Mobile Engineering Internship Assignment

Welcome! This is my submission for the **Wolt Mobile (Android) Engineering Internship 2025** assignment. The app is designed to help users explore nearby dining venues in **Helsinki city center**, while showcasing modern Android development techniques.

---

## Assignment Overview

### Core Features

1. **Dynamic Venue List**:
    - Displays up to **15 venues** near the user's current location.
    - Updates every **10 seconds** as the user's location changes.
    - Loops seamlessly through the provided location list.

2. **Smooth Location Transitions**:
    - Venue list refreshes with smooth animations to enhance visual appeal.

3. **Favorites Management**:
    - Users can toggle venues as **favorites** using a heart icon.
    - Favorite states are persisted across app restarts.

4. **API Integration**:
    - Data fetched from the Wolt API:  
      `https://restaurant-api.wolt.com/v1/pages/restaurants?lat=60.170187&lon=24.930599`
    - Utilizes key fields like venue name, description, image, and ID.

---

## Tech Stack

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Networking**: Ktor
- **Local Database**: Room
- **Dependency Injection**: Hilt
- **State Management**: StateFlow 

---

## Conclusion

Thank you for the opportunity to complete this assignment! It was an exciting challenge to design and implement an application that balances functionality, performance, and user experience. I look forward to discussing my approach, design decisions, and any potential improvements during the next stages of the interview process.

Looking forward to your feedback! 🚀