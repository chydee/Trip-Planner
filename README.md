# Trip Planner

Trip Planner is an Android application designed as part of an interview assessment. The app enables users to create and view trips using a
responsive UI designed from the provided Figma mockups. It integrates with an external API to perform CRUD operations on trip data.

## Features

- **UI Design**: Matches the provided Figma design, with responsive layouts for various screen sizes.
- **API Integration**: Communicates with an external API to create and view trips.
- **Error Handling**: Provides user feedback for successful and failed operations.
- **Built with Modern Android Tools**: Developed using Kotlin and Jetpack Compose.

## Setup Instructions

### Prerequisites

- **Android Studio**: Download and install the latest version of [Android Studio](https://developer.android.com/studio).
- **Git**: Ensure Git is installed on your system.

### Steps

1. Clone the repository:
   ```bash
   git clone git@github.com:chydee/Trip-Planner.git
   cd android-trip-planner
   ```

2. Open the project in Android Studio.

3. Build the project:
  - Allow Gradle to sync dependencies.
  - Connect an Android device or start an emulator.

4. Run the application:
  - Select a device from the Android Studio toolbar.
  - Click the green "Run" button.

## API Documentation

### Base URL

`https://trip-planner.free.beeceptor.com/`

### Endpoints

- **Create Trip**
  - **Method**: `POST`
  - **Endpoint**: `api/trips`

- **View Trips**
  - **Method**: `GET`
  - **Endpoint**: `api/trips`
  
## Libraries Used

- **Jetpack Compose**: UI toolkit for modern Android development.
- **Retrofit**: For API communication.
- **Coroutines**: For asynchronous operations.
- **Hilt**: Dependency injection.

## APK Link

[Download APK](https://github.com/chydee/Trip-Planner/releases/download/v1.0/app-release.apk)