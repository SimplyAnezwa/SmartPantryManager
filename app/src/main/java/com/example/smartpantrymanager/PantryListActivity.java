package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.PantryDataSource;
import com.example.smartpantrymanager.model.PantryItem;

import java.util.List;

public class PantryListActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView tvEmptyPantry;
    private Button btnAddIngredient;
    private EditText etSearchPantry;

    private PantryDataSource pantryDataSource;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry_list);

        // Connect XML views
        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        etSearchPantry = findViewById(R.id.etSearchPantry);

        // Initialise database
        pantryDataSource = new PantryDataSource(this);

        // Configure RecyclerView
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Add Ingredient button
        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PantryListActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        // Search
        etSearchPantry.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        if (pantryAdapter != null) {

                            pantryAdapter.filter(
                                    s.toString()
                            );
                        }
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );

        // Load pantry
        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems =
                pantryDataSource.getAllPantryItems();

        pantryAdapter =
                new PantryAdapter(
                        pantryItems,
                        new PantryAdapter.OnPantryItemActionListener() {

                            @Override
                            public void onEdit(
                                    PantryItem item) {

                                openEditScreen(item);
                            }

                            @Override
                            public void onDelete(
                                    PantryItem item) {

                                confirmDelete(item);
                            }
                        }
                );

        recyclerPantry.setAdapter(
                pantryAdapter
        );

        if (pantryItems.isEmpty()) {

            tvEmptyPantry.setVisibility(
                    TextView.VISIBLE
            );

            recyclerPantry.setVisibility(
                    RecyclerView.GONE
            );

        } else {

            tvEmptyPantry.setVisibility(
                    TextView.GONE
            );

            recyclerPantry.setVisibility(
                    RecyclerView.VISIBLE
            );
        }
    }

    private void openEditScreen(
            PantryItem item) {

        Intent intent = new Intent(
                PantryListActivity.this,
                AddEditIngredientActivity.class
        );

        intent.putExtra(
                "ITEM_ID",
                item.getId()
        );

        startActivity(intent);
    }

    private void confirmDelete(
            PantryItem item) {

        new AlertDialog.Builder(this)

                .setTitle(
                        "Delete Ingredient"
                )

                .setMessage(
                        "Are you sure you want to delete "
                                + item.getName()
                                + "?"
                )

                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            deleteIngredient(item);
                        }
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .show();
    }

    private void deleteIngredient(
            PantryItem item) {

        int deletedRows =
                pantryDataSource.deletePantryItem(
                        item.getId()
                );

        if (deletedRows > 0) {

            Toast.makeText(
                    this,
                    "Ingredient deleted",
                    Toast.LENGTH_SHORT
            ).show();

            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Unable to delete ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}