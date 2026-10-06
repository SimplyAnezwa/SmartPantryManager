package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeDataSource {

    private final DatabaseHelper databaseHelper;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RecipeDataSource(Context context) {

        databaseHelper =
                new DatabaseHelper(context);
    }


    // =========================================================
    // ADD RECIPE
    // =========================================================

    public long addRecipe(Recipe recipe) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "name",
                recipe.getName()
        );

        values.put(
                "description",
                recipe.getDescription()
        );

        values.put(
                "instructions",
                recipe.getInstructions()
        );

        values.put(
                "image_name",
                recipe.getImageName()
        );

        return db.insert(
                "recipes",
                null,
                values
        );
    }


    // =========================================================
    // GET ALL RECIPES
    // =========================================================

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes =
                new ArrayList<>();

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        "recipes",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "name ASC"
                );

        try {

            while (cursor.moveToNext()) {

                Recipe recipe =
                        cursorToRecipe(cursor);

                recipes.add(recipe);
            }

        } finally {

            cursor.close();
        }

        return recipes;
    }


    // =========================================================
    // GET RECIPE BY ID
    // =========================================================

    public Recipe getRecipeById(int recipeId) {

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        "recipes",
                        null,
                        "id = ?",
                        new String[]{
                                String.valueOf(recipeId)
                        },
                        null,
                        null,
                        null
                );

        try {

            if (cursor.moveToFirst()) {

                return cursorToRecipe(cursor);
            }

        } finally {

            cursor.close();
        }

        return null;
    }


    // =========================================================
    // ADD RECIPE INGREDIENT
    // =========================================================

    public long addRecipeIngredient(
            RecipeIngredient ingredient) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "recipe_id",
                ingredient.getRecipeId()
        );

        values.put(
                "ingredient_name",
                ingredient.getIngredientName()
        );

        values.put(
                "required_quantity",
                ingredient.getRequiredQuantity()
        );

        values.put(
                "unit",
                ingredient.getUnit()
        );

        return db.insert(
                "recipe_ingredients",
                null,
                values
        );
    }


    // =========================================================
    // ADD MULTIPLE RECIPE INGREDIENTS
    // =========================================================

    public void addRecipeIngredients(
            List<RecipeIngredient> ingredients) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        db.beginTransaction();

        try {

            for (RecipeIngredient ingredient :
                    ingredients) {

                ContentValues values =
                        new ContentValues();

                values.put(
                        "recipe_id",
                        ingredient.getRecipeId()
                );

                values.put(
                        "ingredient_name",
                        ingredient.getIngredientName()
                );

                values.put(
                        "required_quantity",
                        ingredient.getRequiredQuantity()
                );

                values.put(
                        "unit",
                        ingredient.getUnit()
                );

                db.insert(
                        "recipe_ingredients",
                        null,
                        values
                );
            }

            db.setTransactionSuccessful();

        } finally {

            db.endTransaction();
        }
    }


    // =========================================================
    // GET INGREDIENTS FOR A RECIPE
    // =========================================================

    public List<RecipeIngredient>
    getIngredientsForRecipe(int recipeId) {

        List<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db =
                databaseHelper.getReadableDatabase();

        Cursor cursor =
                db.query(
                        "recipe_ingredients",
                        null,
                        "recipe_id = ?",
                        new String[]{
                                String.valueOf(recipeId)
                        },
                        null,
                        null,
                        "id ASC"
                );

        try {

            while (cursor.moveToNext()) {

                RecipeIngredient ingredient =
                        cursorToRecipeIngredient(
                                cursor
                        );

                ingredients.add(ingredient);
            }

        } finally {

            cursor.close();
        }

        return ingredients;
    }


    // =========================================================
    // DELETE RECIPE
    // =========================================================

    public int deleteRecipe(int recipeId) {

        SQLiteDatabase db =
                databaseHelper.getWritableDatabase();

        return db.delete(
                "recipes",
                "id = ?",
                new String[]{
                        String.valueOf(recipeId)
                }
        );
    }


    // =========================================================
    // CONVERT CURSOR → RECIPE
    // =========================================================

    private Recipe cursorToRecipe(
            Cursor cursor) {

        int id =
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id"
                        )
                );

        String name =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "name"
                        )
                );

        String description =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "description"
                        )
                );

        String instructions =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "instructions"
                        )
                );

        String imageName =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "image_name"
                        )
                );

        return new Recipe(
                id,
                name,
                description,
                instructions,
                imageName
        );
    }


    // =========================================================
    // CONVERT CURSOR → RECIPE INGREDIENT
    // =========================================================

    private RecipeIngredient
    cursorToRecipeIngredient(
            Cursor cursor) {

        int id =
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "id"
                        )
                );

        int recipeId =
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "recipe_id"
                        )
                );

        String ingredientName =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "ingredient_name"
                        )
                );

        double requiredQuantity =
                cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                "required_quantity"
                        )
                );

        String unit =
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "unit"
                        )
                );

        return new RecipeIngredient(
                id,
                recipeId,
                ingredientName,
                requiredQuantity,
                unit
        );
    }
}