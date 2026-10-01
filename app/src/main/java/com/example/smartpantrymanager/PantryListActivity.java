package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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

    private PantryDataSource pantryDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry_list);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        pantryDataSource = new PantryDataSource(this);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadPantryItems();

        btnAddIngredient.setOnClickListener(v -> {
            // Add ingredient functionality will be implemented next.
        });
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems =
                pantryDataSource.getAllPantryItems();

        PantryAdapter adapter =
                new PantryAdapter(pantryItems);

        recyclerPantry.setAdapter(adapter);

        if (pantryItems.isEmpty()) {
            tvEmptyPantry.setVisibility(TextView.VISIBLE);
            recyclerPantry.setVisibility(RecyclerView.GONE);
        } else {
            tvEmptyPantry.setVisibility(TextView.GONE);
            recyclerPantry.setVisibility(RecyclerView.VISIBLE);
        }
    }
}