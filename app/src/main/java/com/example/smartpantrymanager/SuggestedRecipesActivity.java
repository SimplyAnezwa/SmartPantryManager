package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.PantryDataSource;
import com.example.smartpantrymanager.database.RecipeDataSource;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerSuggestedRecipes;
    private TextView tvSuggestedEmpty;

    private RecipeDataSource recipeDataSource;
    private PantryDataSource pantryDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);

        tvSuggestedEmpty =
                findViewById(R.id.tvSuggestedEmpty);

        recipeDataSource =
                new RecipeDataSource(this);

        pantryDataSource =
                new PantryDataSource(this);

        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<Recipe> allRecipes =
                recipeDataSource.getAllRecipes();

        List<PantryItem> pantryItems =
                pantryDataSource.getAllPantryItems();

        List<Recipe> suggestedRecipes =
                new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> requiredIngredients =
                    recipeDataSource.getIngredientsForRecipe(
                            recipe.getId()
                    );

            if (canMakeRecipe(
                    requiredIngredients,
                    pantryItems
            )) {

                suggestedRecipes.add(recipe);
            }
        }

        if (suggestedRecipes.isEmpty()) {

            tvSuggestedEmpty.setVisibility(
                    TextView.VISIBLE
            );

            recyclerSuggestedRecipes.setVisibility(
                    RecyclerView.GONE
            );

            tvSuggestedEmpty.setText(
                    "No suggested recipes yet.\n\n"
                            + "Add ingredients to My Pantry "
                            + "to discover recipes you can make."
            );

        } else {

            tvSuggestedEmpty.setVisibility(
                    TextView.GONE
            );

            recyclerSuggestedRecipes.setVisibility(
                    RecyclerView.VISIBLE
            );

            RecipeAdapter adapter =
                    new RecipeAdapter(
                            suggestedRecipes,
                            recipe -> {

                                Intent intent =
                                        new Intent(
                                                SuggestedRecipesActivity.this,
                                                RecipeDetailActivity.class
                                        );

                                intent.putExtra(
                                        "RECIPE_ID",
                                        recipe.getId()
                                );

                                startActivity(intent);
                            }
                    );

            recyclerSuggestedRecipes.setAdapter(adapter);
        }
    }

    private boolean canMakeRecipe(
            List<RecipeIngredient> requiredIngredients,
            List<PantryItem> pantryItems) {

        if (requiredIngredients == null
                || requiredIngredients.isEmpty()) {

            return false;
        }

        for (RecipeIngredient required :
                requiredIngredients) {

            boolean ingredientAvailable = false;

            String requiredName =
                    required.getIngredientName()
                            .trim()
                            .toLowerCase(Locale.ROOT);

            double requiredQuantity =
                    required.getRequiredQuantity();

            for (PantryItem pantryItem :
                    pantryItems) {

                String pantryName =
                        pantryItem.getName()
                                .trim()
                                .toLowerCase(Locale.ROOT);

                if (pantryName.equals(requiredName)) {

                    double availableQuantity =
                            pantryItem.getQuantity();

                    if (availableQuantity >=
                            requiredQuantity) {

                        ingredientAvailable = true;
                    }

                    break;
                }
            }

            if (!ingredientAvailable) {
                return false;
            }
        }

        return true;
    }
}