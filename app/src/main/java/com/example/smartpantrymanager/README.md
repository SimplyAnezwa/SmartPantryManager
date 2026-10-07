# Smart Pantry Manager

## Project Overview

Smart Pantry Manager is an Android application developed for the Mobile App Development 700 module. The application helps users manage pantry ingredients and discover recipes that can be prepared using the ingredients currently available.

## Technologies Used

- Java
- Android Studio
- XML
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Android Activities and Intents
- Git and GitHub

## Main Features

### Pantry Management
Users can:
- Add pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Specify quantities and units
- Add optional expiry dates
- Search pantry ingredients

### Recipe Management

The application contains a collection of preloaded recipes. Users can:
- Browse recipes
- Search recipes
- View recipe details
- View required ingredients
- View preparation instructions

### Suggested Recipes

The application compares the user's pantry contents with recipe requirements.

A recipe is suggested only when:
- Every required ingredient is available.
- The available quantity is sufficient.
- Ingredient names are normalised to handle common singular and plural forms.
- Compatible units are correctly compared.

If a required ingredient is missing or the available quantity is insufficient, the recipe is excluded from the suggested recipes.

### Settings

The Settings screen provides application settings and application information.

## Database

SQLite is used for persistent local storage.

The database stores:
- Pantry ingredients
- Recipe information
- Recipe ingredients and required quantities

The database is managed using `SQLiteOpenHelper`.

## Project Structure

```text
SmartPantryManager
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── com.example.smartpantrymanager
│           │       ├── adapter
│           │       ├── database
│           │       ├── model
│           │       └── utils
│           └── res
│               └── layout
└── README.md