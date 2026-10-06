package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.RecipeDataSource;
import com.example.smartpantrymanager.model.Recipe;

import java.util.List;

public class RecipeListActivity
        extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView tvEmptyRecipes;
    private EditText etSearchRecipes;

    private RecipeDataSource recipeDataSource;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_list
        );

        recyclerRecipes =
                findViewById(
                        R.id.recyclerRecipes
                );

        tvEmptyRecipes =
                findViewById(
                        R.id.tvEmptyRecipes
                );

        etSearchRecipes =
                findViewById(
                        R.id.etSearchRecipes
                );

        recipeDataSource =
                new RecipeDataSource(this);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        etSearchRecipes.addTextChangedListener(
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

                        if (recipeAdapter != null) {

                            recipeAdapter.filter(
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

        loadRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadRecipes();
    }

    private void loadRecipes() {

        List<Recipe> recipes =
                recipeDataSource.getAllRecipes();

        recipeAdapter =
                new RecipeAdapter(
                        recipes,
                        recipe -> {

                            Intent intent =
                                    new Intent(
                                            RecipeListActivity.this,
                                            RecipeDetailActivity.class
                                    );

                            intent.putExtra(
                                    "RECIPE_ID",
                                    recipe.getId()
                            );

                            startActivity(intent);
                        }
                );

        recyclerRecipes.setAdapter(
                recipeAdapter
        );

        if (recipes.isEmpty()) {

            tvEmptyRecipes.setVisibility(
                    TextView.VISIBLE
            );

            recyclerRecipes.setVisibility(
                    RecyclerView.GONE
            );

        } else {

            tvEmptyRecipes.setVisibility(
                    TextView.GONE
            );

            recyclerRecipes.setVisibility(
                    RecyclerView.VISIBLE
            );
        }
    }
}
