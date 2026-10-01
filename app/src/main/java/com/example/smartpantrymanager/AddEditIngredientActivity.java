package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.PantryDataSource;
import com.example.smartpantrymanager.model.PantryItem;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnSaveIngredient;

    private PantryDataSource pantryDataSource;

    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_ingredient);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        btnSaveIngredient =
                findViewById(R.id.btnSaveIngredient);

        pantryDataSource =
                new PantryDataSource(this);

        itemId =
                getIntent().getIntExtra(
                        "ITEM_ID",
                        -1
                );

        if (itemId != -1) {

            TextView title =
                    findViewById(R.id.tvAddIngredientTitle);

            title.setText("Edit Ingredient");

            btnSaveIngredient.setText("Update Ingredient");

            loadIngredient();

        }

        btnSaveIngredient.setOnClickListener(
                v -> saveIngredient()
        );
    }

    private void loadIngredient() {

        PantryItem item =
                pantryDataSource.getPantryItemById(itemId);

        if (item == null) {
            Toast.makeText(
                    this,
                    "Ingredient not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        etIngredientName.setText(
                item.getName()
        );

        etQuantity.setText(
                String.valueOf(
                        item.getQuantity()
                )
        );

        etUnit.setText(
                item.getUnit()
        );

        etExpiryDate.setText(
                item.getExpiryDate()
        );
    }

    private void saveIngredient() {

        String name =
                etIngredientName.getText()
                        .toString()
                        .trim();

        String quantityText =
                etQuantity.getText()
                        .toString()
                        .trim();

        String unit =
                etUnit.getText()
                        .toString()
                        .trim();

        String expiryDate =
                etExpiryDate.getText()
                        .toString()
                        .trim();

        if (TextUtils.isEmpty(name)) {

            etIngredientName.setError(
                    "Please enter an ingredient name"
            );

            etIngredientName.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(quantityText)) {

            etQuantity.setError(
                    "Please enter a quantity"
            );

            etQuantity.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Please enter a valid number"
            );

            etQuantity.requestFocus();

            return;
        }

        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than zero"
            );

            etQuantity.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(unit)) {

            etUnit.setError(
                    "Please enter a unit"
            );

            etUnit.requestFocus();

            return;
        }

        PantryItem pantryItem =
                new PantryItem(
                        itemId,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

        if (itemId == -1) {

            long insertedId =
                    pantryDataSource.addPantryItem(
                            pantryItem
                    );

            if (insertedId != -1) {

                Toast.makeText(
                        this,
                        "Ingredient added successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Unable to save ingredient",
                        Toast.LENGTH_LONG
                ).show();
            }

        } else {

            int updatedRows =
                    pantryDataSource.updatePantryItem(
                            pantryItem
                    );

            if (updatedRows > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Unable to update ingredient",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}