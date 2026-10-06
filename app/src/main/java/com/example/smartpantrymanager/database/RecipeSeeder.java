package com.example.smartpantrymanager.database;

import android.content.Context;

import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeSeeder {

    private final RecipeDataSource recipeDataSource;

    public RecipeSeeder(Context context) {
        recipeDataSource = new RecipeDataSource(context);
    }

    public void seedRecipes() {

        // Only seed recipes when the database is empty.
        if (!recipeDataSource.getAllRecipes().isEmpty()) {
            return;
        }

        // 1. Vegetable Omelette
        addRecipe(
                "Vegetable Omelette",
                "A simple omelette made with eggs and fresh vegetables.",
                "Beat the eggs. Chop the vegetables. Cook the vegetables in a pan, add the eggs and cook until set.",
                ingredient("Eggs", 2, "pieces"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Onion", 0.5, "piece")
        );

        // 2. Chicken Pasta
        addRecipe(
                "Chicken Pasta",
                "A simple chicken pasta meal with tomato and onion.",
                "Cook the pasta. Cook the chicken with onion and tomato. Combine the cooked pasta with the chicken mixture and serve.",
                ingredient("Chicken", 250, "g"),
                ingredient("Pasta", 200, "g"),
                ingredient("Tomato", 2, "pieces"),
                ingredient("Onion", 1, "piece")
        );

        // 3. Tuna Sandwich
        addRecipe(
                "Tuna Sandwich",
                "A quick sandwich made with tuna, bread and fresh vegetables.",
                "Mix the tuna with your preferred seasoning. Place tuna, tomato and lettuce between two slices of bread and serve.",
                ingredient("Tuna", 1, "can"),
                ingredient("Bread", 2, "slices"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Lettuce", 2, "leaves")
        );

        // 4. Chicken Rice
        addRecipe(
                "Chicken Rice",
                "A filling rice meal made with chicken and vegetables.",
                "Cook the rice. Cook the chicken with onion and carrot. Combine and serve.",
                ingredient("Chicken", 250, "g"),
                ingredient("Rice", 200, "g"),
                ingredient("Onion", 1, "piece"),
                ingredient("Carrot", 1, "piece")
        );

        // 5. Scrambled Eggs
        addRecipe(
                "Scrambled Eggs",
                "Quick scrambled eggs for breakfast.",
                "Crack the eggs into a bowl and beat them. Cook in a pan while stirring until scrambled and cooked.",
                ingredient("Eggs", 2, "pieces")
        );

        // 6. Tomato Egg Toast
        addRecipe(
                "Tomato Egg Toast",
                "Toast topped with egg and fresh tomato.",
                "Toast the bread. Cook the egg. Add sliced tomato and the cooked egg on top of the toast.",
                ingredient("Bread", 2, "slices"),
                ingredient("Eggs", 2, "pieces"),
                ingredient("Tomato", 1, "piece")
        );

        // 7. Chicken Sandwich
        addRecipe(
                "Chicken Sandwich",
                "A simple chicken sandwich with tomato and lettuce.",
                "Cook the chicken. Place chicken, tomato and lettuce between slices of bread and serve.",
                ingredient("Chicken", 150, "g"),
                ingredient("Bread", 2, "slices"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Lettuce", 2, "leaves")
        );

        // 8. Vegetable Rice
        addRecipe(
                "Vegetable Rice",
                "Rice combined with fresh vegetables.",
                "Cook the rice. Chop and cook the vegetables. Mix the vegetables with the cooked rice and serve.",
                ingredient("Rice", 200, "g"),
                ingredient("Carrot", 1, "piece"),
                ingredient("Onion", 1, "piece"),
                ingredient("Tomato", 1, "piece")
        );

        // 9. Tuna Rice
        addRecipe(
                "Tuna Rice",
                "A quick rice meal made with canned tuna and vegetables.",
                "Cook the rice. Heat the tuna with chopped onion and tomato. Combine with the rice and serve.",
                ingredient("Tuna", 1, "can"),
                ingredient("Rice", 200, "g"),
                ingredient("Onion", 1, "piece"),
                ingredient("Tomato", 1, "piece")
        );

        // 10. Chicken and Carrot Stir Fry
        addRecipe(
                "Chicken and Carrot Stir Fry",
                "Chicken stir-fried with carrots and onion.",
                "Slice the chicken and vegetables. Stir-fry the chicken until cooked, then add the vegetables and cook until tender.",
                ingredient("Chicken", 250, "g"),
                ingredient("Carrot", 2, "pieces"),
                ingredient("Onion", 1, "piece")
        );

        // 11. Egg Fried Rice
        addRecipe(
                "Egg Fried Rice",
                "Rice stir-fried with eggs, onion and carrot.",
                "Cook the rice. Scramble the eggs. Stir-fry the onion and carrot, add the rice and eggs, then combine.",
                ingredient("Rice", 200, "g"),
                ingredient("Eggs", 2, "pieces"),
                ingredient("Onion", 1, "piece"),
                ingredient("Carrot", 1, "piece")
        );

        // 12. Tomato Pasta
        addRecipe(
                "Tomato Pasta",
                "A simple pasta dish with tomato and onion.",
                "Cook the pasta. Cook the chopped tomato and onion in a pan. Add the pasta and mix well before serving.",
                ingredient("Pasta", 200, "g"),
                ingredient("Tomato", 2, "pieces"),
                ingredient("Onion", 1, "piece")
        );

        // 13. Tuna Pasta
        addRecipe(
                "Tuna Pasta",
                "Pasta combined with tuna, tomato and onion.",
                "Cook the pasta. Cook the tomato and onion, then add the tuna. Mix with the cooked pasta and serve.",
                ingredient("Tuna", 1, "can"),
                ingredient("Pasta", 200, "g"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Onion", 1, "piece")
        );

        // 14. Vegetable Sandwich
        addRecipe(
                "Vegetable Sandwich",
                "A fresh sandwich made with bread and vegetables.",
                "Slice the vegetables. Place tomato, lettuce, carrot and onion between two slices of bread.",
                ingredient("Bread", 2, "slices"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Lettuce", 2, "leaves"),
                ingredient("Carrot", 1, "piece"),
                ingredient("Onion", 0.5, "piece")
        );

        // 15. Chicken Tomato Pasta
        addRecipe(
                "Chicken Tomato Pasta",
                "Pasta with chicken, tomato and onion.",
                "Cook the pasta. Cook the chicken with onion and tomato. Combine the chicken mixture with the pasta and serve.",
                ingredient("Chicken", 250, "g"),
                ingredient("Pasta", 200, "g"),
                ingredient("Tomato", 2, "pieces"),
                ingredient("Onion", 1, "piece")
        );

        // 16. Chicken and Rice Bowl
        addRecipe(
                "Chicken and Rice Bowl",
                "A simple bowl containing chicken, rice and vegetables.",
                "Cook the rice and chicken separately. Cook the onion and carrot, then combine everything in a bowl.",
                ingredient("Chicken", 200, "g"),
                ingredient("Rice", 200, "g"),
                ingredient("Carrot", 1, "piece"),
                ingredient("Onion", 1, "piece")
        );

        // 17. Tomato Rice
        addRecipe(
                "Tomato Rice",
                "Rice cooked with tomato and onion.",
                "Cook the rice. Cook the chopped tomato and onion until soft. Mix with the rice and serve.",
                ingredient("Rice", 200, "g"),
                ingredient("Tomato", 2, "pieces"),
                ingredient("Onion", 1, "piece")
        );

        // 18. Egg and Tuna Sandwich
        addRecipe(
                "Egg and Tuna Sandwich",
                "A protein-rich sandwich combining tuna and egg.",
                "Boil or cook the eggs. Mix the tuna with the eggs. Place the mixture between slices of bread and serve.",
                ingredient("Tuna", 1, "can"),
                ingredient("Eggs", 2, "pieces"),
                ingredient("Bread", 2, "slices")
        );

        // 19. Chicken Vegetable Rice
        addRecipe(
                "Chicken Vegetable Rice",
                "Rice with chicken and a selection of vegetables.",
                "Cook the rice. Cook the chicken, onion, carrot and tomato. Combine everything and serve.",
                ingredient("Chicken", 250, "g"),
                ingredient("Rice", 200, "g"),
                ingredient("Carrot", 1, "piece"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Onion", 1, "piece")
        );

        // 20. Vegetable Egg Rice
        addRecipe(
                "Vegetable Egg Rice",
                "Rice with scrambled eggs and fresh vegetables.",
                "Cook the rice. Scramble the eggs. Cook the onion, carrot and tomato, then combine everything.",
                ingredient("Rice", 200, "g"),
                ingredient("Eggs", 2, "pieces"),
                ingredient("Carrot", 1, "piece"),
                ingredient("Tomato", 1, "piece"),
                ingredient("Onion", 1, "piece")
        );
    }

    private void addRecipe(
            String name,
            String description,
            String instructions,
            RecipeIngredient... ingredients) {

        Recipe recipe = new Recipe(
                -1,
                name,
                description,
                instructions,
                ""
        );

        long recipeId =
                recipeDataSource.addRecipe(recipe);

        if (recipeId != -1) {

            List<RecipeIngredient> ingredientList =
                    new ArrayList<>();

            for (RecipeIngredient ingredient : ingredients) {

                ingredientList.add(
                        new RecipeIngredient(
                                -1,
                                (int) recipeId,
                                ingredient.getIngredientName(),
                                ingredient.getRequiredQuantity(),
                                ingredient.getUnit()
                        )
                );
            }

            recipeDataSource.addRecipeIngredients(
                    ingredientList
            );
        }
    }

    private RecipeIngredient ingredient(
            String name,
            double quantity,
            String unit) {

        return new RecipeIngredient(
                -1,
                -1,
                name,
                quantity,
                unit
        );
    }
}