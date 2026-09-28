# smart pantry manager mobile app

This mart Pantry Manager is basically an android application that helps users keep track of pantry ingredients and give them their suggested recipes that can be made using the ingredients currently available

This application was developed in Java using Android Studio

## features

- Add pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Stores quantity and measurement unit
- Expiry status
- Recipe suggestions based on items
- Recipe matching
- Recipe ingredient and how to make recipe
- Light and dark mode
- Uses SQLite

## recipe Matching

A recipe is only suggested when all the ingredients required for that recipe are available and if there is enough of that ingredient.

even If one ingredient is missing then the recipe will not be suggested

## pantry

Each pantry item can store:

- Ingredient name
- Quantity
- Unit
- Expiry date

Pantry information is stored locally using SQLite so that the data remains available when the application is closed even

## recipes

Each recipe contains :

- Recipe name
- Required ingredients
- Required quantitied
- Measurement units
- and how to make it

Users can select a suggested recipe to view its full ingredient list and preparation method

## expiry Dates

in this app depenndign on the exipery date it will show:

- Expires:
- Is expiring soon (wihtin 7 days)
- Has already expired

## navigation or the drawer

- Pantry
- Suggested Recipes
- Settings

## dark Mode

The Settings screen allows the user to enable or disable dark mode. the appilication remembers settings

## Technologies Used

- Java
- Android Studio
- SQLite
- RecyclerView
- SharedPreferences
- Material Components
- XML layouts

## project Structure

- activities - application screens
- adapters - recycler view adapters
- database - sqlite database handling
- models - pantry and recipe data models
- utils - recipe matching logic

## running the Application

1. Open the project in Android Studio
2. Start an Android emulator or connect an Android device
3. Run the application
4. Add pantry ingredients and open Suggested Recipes to view recipes that can be made for you

enjoy!!