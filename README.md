# Grocery List (Shopr)

A simple Android shopping list application that lets you manage grocery items by category, mark them complete, edit, delete, and persist data between launches.

## Features

- **Add items**: Enter a name, choose a category (Milk, Vegetables, Fruits, Breads, Meats), and add with one tap. Input clears after adding.
- **List view**: All items show name and category; empty state when the list is empty.
- **Manage items**: Mark as purchased (checkbox), delete (with confirmation), edit name and category.
- **Filter & sort**: Filter by category; sort by default (newest first), name (A–Z / Z–A), category, or completion status.
- **Persistence**: Data is stored locally with Room and survives app restarts.

## Tech Stack

- **Kotlin**
- **Jetpack Compose** for UI
- **MVI** (Model-View-Intent): single state (`GroceryListUiState`), intents (`GroceryListIntent`), ViewModel reduces intents to state
- **Koin** for dependency injection
- **Room** for local database
- **KSP** for Room code generation

## Requirements

- Android Studio Ladybug (2024.2.1) or newer (or compatible AGP 8.x)
- JDK 11+
- minSdk 26, targetSdk 36

## Build and Run

1. Clone the repository (or open the project in Android Studio).
2. Open the project in Android Studio and sync Gradle.
3. Connect a device or start an emulator (API 26+).
4. Run the app:
   - **From IDE**: Click **Run** (green triangle) or use `Shift+F10` (Windows/Linux) / `Control+R` (Mac).
   - **From command line**:
     ```bash
     ./gradlew installDebug
     ```
     Then launch the “Grocery List” app on the device.

## Build variants

- **Debug APK**: `./gradlew assembleDebug`  
  Output: `app/build/outputs/apk/debug/app-debug.apk`
- **Release**: `./gradlew assembleRelease`  
  (Configure signing in `app/build.gradle.kts` for release builds.)

## Tests

- **Unit tests** (Repository and ViewModel):
  ```bash
  ./gradlew testDebugUnitTest
  ```

## Project structure

- `app/src/main/java/com/propertyfinder/shopr/`
  - **data/**: `GroceryItem`, `GroceryCategory`, `GroceryDao`, `AppDatabase`, `GroceryRepository`, `Converters`
  - **di/**: `AppModule` (Koin module: database, DAO, repository, ViewModel)
  - **ui/**: `GroceryListIntent` (user actions), `GroceryListViewModel` (dispatch → state), `GroceryListScreen` (renders state, dispatches intents)
  - **ui/theme/**: Theme, colors, typography
  - `MainActivity.kt`, `ShoprApplication.kt` (Koin started in `Application`)

## License

This project is for assignment/portfolio use.
