package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartpantrymanager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PANTRY = "pantry_items";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";

    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_METHOD = "method";
    public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
    public static final String COLUMN_REQUIRED_QUANTITY = "required_quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createPantryTable(db);
        createRecipeTables(db);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {
            createRecipeTables(db);
            seedRecipes(db);
        }
    }

    private void createPantryTable(SQLiteDatabase db) {

        String createPantryTable =
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_QUANTITY + " REAL NOT NULL, " +
                        COLUMN_UNIT + " TEXT NOT NULL, " +
                        COLUMN_EXPIRY_DATE + " TEXT" +
                        ")";

        db.execSQL(createPantryTable);
    }

    private void createRecipeTables(SQLiteDatabase db) {

        String createRecipesTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPES + " (" +
                        COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_METHOD +
                        " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_ID +
                        " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_REQUIRED_QUANTITY +
                        " REAL NOT NULL, " +
                        COLUMN_UNIT +
                        " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);
    }

    private void seedRecipes(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        int recipeCount = 0;

        if (cursor.moveToFirst()) {
            recipeCount = cursor.getInt(0);
        }

        cursor.close();

        if (recipeCount > 0) {
            return;
        }

        addRecipe(
                db,
                "scrambled gggs",
                "jsut beat eggs. Melt the butter in a pan. add the eggs and cook while stiring gently. season with salt.",
                new String[]{"eggs", "butter", "salt"},
                new double[]{2, 10, 1},
                new String[]{"pcs", "g", "g"}
        );

        addRecipe(
                db,
                "cheese oelette",
                "just beat eggs. melt the butter in a pan and add the eggs. add cheese and salt, then fold for omelette.",
                new String[]{"eggs", "cheese", "butter", "salt"},
                new double[]{2, 30, 10, 1},
                new String[]{"pcs", "g", "g", "g"}
        );

        addRecipe(
                db,
                "egg on toast",
                "toast the bread.cook the eggs and serve them on the toast with butter.",
                new String[]{"eggs", "bread", "butter"},
                new double[]{2, 2, 10},
                new String[]{"pcs", "slices", "g"}
        );

        addRecipe(
                db,
                "cheese toast",
                "butter the bread, add cheese and toast until the cheese has melted.",
                new String[]{"bread", "cheese", "butter"},
                new double[]{2, 40, 10},
                new String[]{"slices", "g", "g"}
        );

        addRecipe(
                db,
                "peanut butter toast",
                "toast the bread and spread the peanut butter over each slice.",
                new String[]{"bread", "peanut butter"},
                new double[]{2, 30},
                new String[]{"slices", "g"}
        );

        addRecipe(
                db,
                "french toast",
                "just beat the egg with the milk. dip the bread into the mixture and cook in a pan until golden.",
                new String[]{"bread", "eggs", "milk"},
                new double[]{2, 1, 100},
                new String[]{"slices", "pcs", "ml"}
        );

        addRecipe(
                db,
                "tuna sandwich",
                "mix tuna and mayonnaise together and place the mixture between the bread slices.",
                new String[]{"bread", "tuna", "mayonnaise"},
                new double[]{2, 100, 20},
                new String[]{"slices", "g", "g"}
        );

        addRecipe(
                db,
                "cheese sandwich",
                "butter the bread, add the cheese and place the second slice of bread on top.",
                new String[]{"bread", "cheese", "butter"},
                new double[]{2, 40, 10},
                new String[]{"slices", "g", "g"}
        );

        addRecipe(
                db,
                "chicken and rice",
                "cook rice. cook the chicken in oil until fully cooked, season with salt and serve with the rice.",
                new String[]{"chicken", "rice", "oil", "salt"},
                new double[]{150, 100, 10, 1},
                new String[]{"g", "g", "ml", "g"}
        );

        addRecipe(
                db,
                "egg fried rice",
                "cook eggs in oil, add the cooked rice and stir-fry until heated through.",
                new String[]{"rice", "eggs", "oil"},
                new double[]{150, 2, 10},
                new String[]{"g", "pcs", "ml"}
        );

        addRecipe(
                db,
                "tuna rice bowl",
                "Cook rice, add tuna and mayonnaise and mix together.",
                new String[]{"rice", "tuna", "mayonnaise"},
                new double[]{150, 100, 20},
                new String[]{"g", "g", "g"}
        );

        addRecipe(
                db,
                "tomato pasta",
                "cook pasta. cook the tomatoes in oil, season with salt and mix with the pasta.",
                new String[]{"pasta", "tomato", "oil", "salt"},
                new double[]{100, 2, 10, 1},
                new String[]{"g", "pcs", "ml", "g"}
        );

        addRecipe(
                db,
                "cheesy pasta",
                "cook pasta. add cheese, milk and butter and stir until creamy.",
                new String[]{"pasta", "cheese", "milk", "butter"},
                new double[]{100, 50, 100, 10},
                new String[]{"g", "g", "ml", "g"}
        );

        addRecipe(
                db,
                "chicken pasta",
                "cook pasta. cook chicken and tomatoes, and then we just combine everything together.",
                new String[]{"pasta", "chicken", "tomato"},
                new double[]{100, 150, 2},
                new String[]{"g", "g", "pcs"}
        );

        addRecipe(
                db,
                "mashed potatoes",
                "boil potatoes until soft. mash them with milk, butter and salt.",
                new String[]{"potatoes", "milk", "butter", "salt"},
                new double[]{3, 100, 20, 1},
                new String[]{"pcs", "ml", "g", "g"}
        );

        addRecipe(
                db,
                "fried potatoes",
                "cut potatoes and fry them in oil until golden. season with salt.",
                new String[]{"potatoes", "oil", "salt"},
                new double[]{3, 20, 1},
                new String[]{"pcs", "ml", "g"}
        );

        addRecipe(
                db,
                "tomato and egg",
                "cook tomatoes in oil, add the eggs and cook until the eggs are done.",
                new String[]{"tomato", "eggs", "oil"},
                new double[]{2, 2, 10},
                new String[]{"pcs", "pcs", "ml"}
        );

        addRecipe(
                db,
                "Pancakes",
                "mix flour, egg, milk and sugar into a batter. cook portions of the batter in a pan.",
                new String[]{"flour", "eggs", "milk", "sugar"},
                new double[]{120, 1, 200, 20},
                new String[]{"g", "pcs", "ml", "g"}
        );

        addRecipe(
                db,
                "porridge oats",
                "cook oats with milk until soft and creamy. stir in the sugar.",
                new String[]{"oats", "milk", "sugar"},
                new double[]{80, 250, 15},
                new String[]{"g", "ml", "g"}
        );

        addRecipe(
                db,
                "banana oats",
                "cook oats with milk until soft. slice banana and add it before serving.",
                new String[]{"oats", "milk", "banana"},
                new double[]{80, 250, 1},
                new String[]{"g", "ml", "pcs"}
        );
    }

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String method,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues =
                new ContentValues();

        recipeValues.put(
                COLUMN_NAME,
                name
        );

        recipeValues.put(
                COLUMN_METHOD,
                method
        );

        long recipeId =
                db.insert(
                        TABLE_RECIPES,
                        null,
                        recipeValues
                );

        for (int i = 0;
             i < ingredientNames.length;
             i++) {

            ContentValues ingredientValues =
                    new ContentValues();

            ingredientValues.put(
                    COLUMN_RECIPE_ID,
                    recipeId
            );

            ingredientValues.put(
                    COLUMN_INGREDIENT_NAME,
                    ingredientNames[i]
            );

            ingredientValues.put(
                    COLUMN_REQUIRED_QUANTITY,
                    quantities[i]
            );

            ingredientValues.put(
                    COLUMN_UNIT,
                    units[i]
            );

            db.insert(
                    TABLE_RECIPE_INGREDIENTS,
                    null,
                    ingredientValues
            );
        }
    }

    //add a new pantry item
    public long addPantryItem(PantryItem item) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_NAME,
                item.getName()
        );

        values.put(
                COLUMN_QUANTITY,
                item.getQuantity()
        );

        values.put(
                COLUMN_UNIT,
                item.getUnit()
        );

        values.put(
                COLUMN_EXPIRY_DATE,
                item.getExpiryDate()
        );

        long result =
                db.insert(
                        TABLE_PANTRY,
                        null,
                        values
                );

        db.close();

        return result;
    }

    //returns all pantry items
    public List<PantryItem> getAllPantryItems() {

        List<PantryItem> pantryItems =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_PANTRY,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_NAME + " ASC"
                );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_UNIT
                                )
                        );

                String expiryDate =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_EXPIRY_DATE
                                )
                        );

                PantryItem item =
                        new PantryItem(
                                id,
                                name,
                                quantity,
                                unit,
                                expiryDate
                        );

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pantryItems;
    }

    //returns one pantry item using its id
    public PantryItem getPantryItem(int id) {

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_PANTRY,
                        null,
                        COLUMN_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        },
                        null,
                        null,
                        null
                );

        PantryItem item = null;

        if (cursor.moveToFirst()) {

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_NAME
                            )
                    );

            double quantity =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_QUANTITY
                            )
                    );

            String unit =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_UNIT
                            )
                    );

            String expiryDate =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    COLUMN_EXPIRY_DATE
                            )
                    );

            item =
                    new PantryItem(
                            id,
                            name,
                            quantity,
                            unit,
                            expiryDate
                    );
        }

        cursor.close();
        db.close();

        return item;
    }

    //updates an existing pantry item
    public int updatePantryItem(PantryItem item) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_NAME,
                item.getName()
        );

        values.put(
                COLUMN_QUANTITY,
                item.getQuantity()
        );

        values.put(
                COLUMN_UNIT,
                item.getUnit()
        );

        values.put(
                COLUMN_EXPIRY_DATE,
                item.getExpiryDate()
        );

        int result =
                db.update(
                        TABLE_PANTRY,
                        values,
                        COLUMN_ID + " = ?",
                        new String[]{
                                String.valueOf(
                                        item.getId()
                                )
                        }
                );

        db.close();

        return result;
    }

    //deletes a pantry item
    public int deletePantryItem(int id) {

        SQLiteDatabase db =
                getWritableDatabase();

        int result =
                db.delete(
                        TABLE_PANTRY,
                        COLUMN_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        db.close();

        return result;
    }
}