package com.example.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "smart_pantry.db";

    private static final int DATABASE_VERSION = 2;


    // =========================================================
    // PANTRY TABLE
    // =========================================================

    public static final String TABLE_PANTRY_ITEMS =
            "pantry_items";

    public static final String COLUMN_ID =
            "id";

    public static final String COLUMN_NAME =
            "name";

    public static final String COLUMN_QUANTITY =
            "quantity";

    public static final String COLUMN_UNIT =
            "unit";

    public static final String COLUMN_EXPIRY_DATE =
            "expiry_date";


    private static final String CREATE_PANTRY_TABLE =
            "CREATE TABLE " + TABLE_PANTRY_ITEMS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL, " +
                    COLUMN_EXPIRY_DATE + " TEXT" +
                    ")";


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // =========================================================
    // CREATE DATABASE TABLES
    // =========================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // -----------------------------------------------------
        // Pantry table
        // -----------------------------------------------------

        db.execSQL(
                CREATE_PANTRY_TABLE
        );


        // -----------------------------------------------------
        // Recipes table
        // -----------------------------------------------------

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "instructions TEXT, " +
                        "image_name TEXT)"
        );


        // -----------------------------------------------------
        // Recipe ingredients table
        // -----------------------------------------------------

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "required_quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) " +
                        "REFERENCES recipes(id) " +
                        "ON DELETE CASCADE)"
        );
    }


    // =========================================================
    // DATABASE UPGRADE
    // =========================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {

            // -------------------------------------------------
            // Create recipes table
            // -------------------------------------------------

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS recipes (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT NOT NULL, " +
                            "description TEXT, " +
                            "instructions TEXT, " +
                            "image_name TEXT)"
            );


            // -------------------------------------------------
            // Create recipe ingredients table
            // -------------------------------------------------

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "recipe_id INTEGER NOT NULL, " +
                            "ingredient_name TEXT NOT NULL, " +
                            "required_quantity REAL NOT NULL, " +
                            "unit TEXT NOT NULL, " +
                            "FOREIGN KEY(recipe_id) " +
                            "REFERENCES recipes(id) " +
                            "ON DELETE CASCADE)"
            );
        }
    }
    public boolean isRecipeTableEmpty(SQLiteDatabase db) {

        android.database.Cursor cursor = null;

        try {

            cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM recipes",
                    null
            );

            if (cursor.moveToFirst()) {

                return cursor.getInt(0) == 0;
            }

        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }

        return true;
    }
}