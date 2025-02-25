# Amphibians Compose

<img src="https://github.com/user-attachments/assets/e9be9ade-6999-4856-b48b-77e232040087" width="200">


## Overview
Amphibians Compose is an Android application developed using Kotlin and Jetpack Compose. 
The app fetches data about various amphibian species from a remote API and displays it.

## Features

**Display Amphibian List:** Present a scrollable list of amphibians with their names and images.

## Technologies Used
**Kotlin:** Programming language for Android development.

**Jetpack Compose:** Modern toolkit for building native Android UI.

**Retrofit**: Type-safe HTTP client for making API requests.

**Coil:** Image loading library for Android.

## Getting Started

### Prerequisites
Android Studio 
Android device or emulator 

### Installation

**1-Clone the repository:**
 ```
git clone https://github.com/berkeyilmaz1/amphibians_compose.git
```
**2-Open the project in Android Studio:**

**3-Launch Android Studio.**

**4-Click on "Open an existing project".**

**5-Navigate to the cloned repository folder and select it.**

**6-Build and run the app**


## Project Structure
The project follows the MVVM (Model-View-ViewModel) architecture pattern:

**Model:** Contains data classes representing the amphibian data.

**View:** Comprises Composable functions that define the UI.

**ViewModel:** Holds the UI state and handles logic, including data fetching from the repository.

## API Reference
The app fetches data from the following API endpoint:
```
GET https://android-kotlin-fun-mars-server.appspot.com/amphibians
```
## Acknowledgements

This project is inspired by the Android Basics with Compose course.
