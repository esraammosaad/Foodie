# Foodie - Meal Planner App
### Foodie is an Android application designed to help users plan their meals efficiently. It offers various features such as meal recommendations, ingredient-based searches, favorites, calendar planning, authentication, and offline support.
# Features
- Random Meal Generator: Discover new meal ideas with a random meal suggestion.

- Search by Ingredient, Area, and Category: Easily find meals based on ingredients, cuisines, or categories.

- Favorites: Save meals to your favorites for quick access.

- Meal Planner with Calendar Integration: Add meals to your mobile calendar using a content provider and remove them when necessary.

- Profile Screen: Manage your profile, share the app, contact support, and toggle dark mode.

- Authentication: Sign in/up using Firebase authentication (email & password, Google login), email verification, and password recovery.

- Guest Mode: Browse the app without authentication (favorites and calendar features disabled).

- Offline Support: If the internet is unavailable, cached data is retrieved via OkHttp.

- Sync Data Across Devices: Favorites and calendar meals are stored in Firestore for cross-device accessibility.

- Internet Connectivity Handling: Uses a Broadcast Receiver to detect and handle internet disruptions.
# Technologies Used

- Java: Core language for app development.

- Android Framework: Standard Android development libraries.

- MVP Architecture: Ensures separation of concerns for better maintainability.

- RxJava: Handles asynchronous operations efficiently.

- Room Database: Local database for storing app data.

- Retrofit: API client for network requests.

- OkHttp Caching: Enables offline data retrieval.

- Firebase Authentication: Secure login with email/password and Google authentication.

- Firestore Database: Stores user data for synchronization.

- Content Provider: Manages calendar meal planning integration.

- Broadcast Receiver: Detects network changes and provides real-time responses.
# Installation
### git clone https://github.com/your-repo/foodie-meal-planner.git
# Mockup
![Mockup 04](https://github.com/user-attachments/assets/815ac5ac-bda9-4768-b38d-6b2b74c9f927)
# Demo
https://github.com/user-attachments/assets/5ffb510c-d222-40a2-a347-d2122407c8ac


