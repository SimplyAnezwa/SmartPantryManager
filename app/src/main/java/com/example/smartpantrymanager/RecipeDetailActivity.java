package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.RecipeDataSource;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity
        extends AppCompatActivity {

    private TextView tvRecipeName;
    private TextView tvRecipeDescription;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeInstructions;

    private RecipeDataSource recipeDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        tvRecipeName =
                findViewById(
                        R.id.tvRecipeDetailName
                );

        tvRecipeDescription =
                findViewById(
                        R.id.tvRecipeDetailDescription
                );

        tvRecipeIngredients =
                findViewById(
                        R.id.tvRecipeDetailIngredients
                );

        tvRecipeInstructions =
                findViewById(
                        R.id.tvRecipeDetailInstructions
                );

        recipeDataSource =
                new RecipeDataSource(this);

        int recipeId =
                getIntent().getIntExtra(
                        "RECIPE_ID",
                        -1
                );

        if (recipeId != -1) {

            loadRecipe(recipeId);
        }
    }

    private void loadRecipe(int recipeId) {

        Recipe recipe =
                recipeDataSource.getRecipeById(
                        recipeId
                );

        if (recipe == null) {

            tvRecipeName.setText(
                    "Recipe not found"
            );

            return;
        }

        tvRecipeName.setText(
                recipe.getName()
        );

        tvRecipeDescription.setText(
                recipe.getDescription()
        );

        tvRecipeInstructions.setText(
                recipe.getInstructions()
        );

        loadIngredients(recipeId);
    }

    private void loadIngredients(
            int recipeId) {

        List<RecipeIngredient> ingredients =
                recipeDataSource
                        .getIngredientsForRecipe(
                                recipeId
                        );

        StringBuilder ingredientText =
                new StringBuilder();

        for (RecipeIngredient ingredient :
                ingredients) {

            ingredientText
                    .append("• ")
                    .append(
                            ingredient
                                    .getIngredientName()
                    )
                    .append(" — ")
                    .append(
                            ingredient
                                    .getRequiredQuantity()
                    )
                    .append(" ")
                    .append(
                            ingredient.getUnit()
                    )
                    .append("\n");
        }

        if (ingredientText.length() == 0) {

            ingredientText.append(
                    "No ingredients listed."
            );
        }

        tvRecipeIngredients.setText(
                ingredientText.toString()
        );
    }
}