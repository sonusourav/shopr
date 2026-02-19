# Shopr

A simple Android shopping list application that lets you manage grocery items by category, mark them complete, edit, delete, and persist data between launches.

## Screenshots

<img src="https://github.com/user-attachments/assets/b9032931-33a4-435d-bf82-6cc6c644d26f" width="100" height="200" />

<img src="https://github.com/user-attachments/assets/fbf6731e-d5d0-4a32-8dec-fda889ec04bf" width="100" height="200" />

<img src="https://github.com/user-attachments/assets/12eed6c0-fd4e-4c9f-9875-cd4ab41db0ca" width="100" height="200" />

<img src="https://github.com/user-attachments/assets/c9816d89-6ee8-4f0e-b3e4-48780e7d1679" width="100" height="200" />

<img src="https://github.com/user-attachments/assets/1f23ae6f-14c2-42ad-a390-dd7103bd6b78" width="100" height="200" />

<img src="https://github.com/user-attachments/assets/dbb2529c-f733-4abe-8e4f-7bb8c22072ca" width="100" height="200" />

## Features

- **Add items**: Enter a name, choose a category (Milk, Vegetables, Fruits, Breads, Meats), and add with one tap. Input clears after adding.
- **List view**: All items show name and category; empty state when the list is empty.
- **Manage items**: Mark as purchased (checkbox), delete (with confirmation), edit name and category.
- **Filter & sort**: Filter by category; sort by default (newest first), name (A–Z / Z–A), category, or completion status.
- **Persistence**: Data is stored locally with Room and survives app restarts.

## How to: Edit, Mark Completed, Delete

- **Edit an item**
  1. Long-press the item row (only for items not yet marked completed).
  2. The “Add New Item” card at the top switches to edit mode and shows the item’s name and category.
  3. Change the name and/or category, then tap **Update**. A toast confirms the update (e.g. “Oat Milk updated”).

- **Mark as completed (purchased)**
  1. Tap the **check** (✓) icon on the item row.
  2. The item is marked as purchased (row style changes; check icon is hidden). A toast shows (e.g. “Milk marked as purchased”).

- **Delete an item**
  1. Tap the **delete** (trash) icon on the item row.
  2. In the confirmation dialog, tap **Delete** to remove the item (or **Cancel** to keep it). A toast confirms removal (e.g. “Bread removed”).

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

