package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.RecipeSeeder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed starter recipes into the database
        RecipeSeeder recipeSeeder = new RecipeSeeder(this);
        recipeSeeder.seedRecipes();

        // My Pantry
        Button btnPantry = findViewById(R.id.btnPantry);

        btnPantry.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    PantryListActivity.class
            );
            startActivity(intent);
        });

        // Suggested Recipes
        Button btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        // View Recipes
        Button btnViewRecipes =
                findViewById(R.id.btnViewRecipes);

        btnViewRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    RecipeListActivity.class
            );
            startActivity(intent);
        });

        // Settings
        Button btnSettings =
                findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }
}