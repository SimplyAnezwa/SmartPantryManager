package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryDataSource {

    private final DatabaseHelper databaseHelper;

    public PantryDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    // CREATE
    public long addPantryItem(PantryItem item) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_NAME, item.getName());
        values.put(DatabaseHelper.COLUMN_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COLUMN_UNIT, item.getUnit());
        values.put(DatabaseHelper.COLUMN_EXPIRY_DATE, item.getExpiryDate());

        long id = db.insert(
                DatabaseHelper.TABLE_PANTRY_ITEMS,
                null,
                values
        );

        db.close();

        return id;
    }

    // READ - all items
    public List<PantryItem> getAllPantryItems() {

        List<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PANTRY_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                PantryItem item = new PantryItem();

                item.setId(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COLUMN_ID
                                )
                        )
                );

                item.setName(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COLUMN_NAME
                                )
                        )
                );

                item.setQuantity(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COLUMN_QUANTITY
                                )
                        )
                );

                item.setUnit(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COLUMN_UNIT
                                )
                        )
                );

                item.setExpiryDate(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COLUMN_EXPIRY_DATE
                                )
                        )
                );

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pantryItems;
    }

    // READ - one item
    public PantryItem getPantryItemById(int id) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PANTRY_ITEMS,
                null,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        PantryItem item = null;

        if (cursor.moveToFirst()) {

            item = new PantryItem();

            item.setId(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_ID
                            )
                    )
            );

            item.setName(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_NAME
                            )
                    )
            );

            item.setQuantity(
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_QUANTITY
                            )
                    )
            );

            item.setUnit(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_UNIT
                            )
                    )
            );

            item.setExpiryDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseHelper.COLUMN_EXPIRY_DATE
                            )
                    )
            );
        }

        cursor.close();
        db.close();

        return item;
    }

    // UPDATE
    public int updatePantryItem(PantryItem item) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_NAME, item.getName());
        values.put(DatabaseHelper.COLUMN_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COLUMN_UNIT, item.getUnit());
        values.put(DatabaseHelper.COLUMN_EXPIRY_DATE, item.getExpiryDate());

        int rowsUpdated = db.update(
                DatabaseHelper.TABLE_PANTRY_ITEMS,
                values,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return rowsUpdated;
    }

    // DELETE
    public int deletePantryItem(int id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                DatabaseHelper.TABLE_PANTRY_ITEMS,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsDeleted;
    }
}