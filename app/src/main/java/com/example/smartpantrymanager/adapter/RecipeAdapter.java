package com.example.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> recipes;
    private final List<Recipe> allRecipes;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private final OnRecipeClickListener listener;

    public RecipeAdapter(
            List<Recipe> recipes,
            OnRecipeClickListener listener) {

        this.recipes =
                new ArrayList<>(recipes);

        this.allRecipes =
                new ArrayList<>(recipes);

        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_recipe,
                        parent,
                        false
                );

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe =
                recipes.get(position);

        holder.tvRecipeName.setText(
                recipe.getName()
        );

        holder.tvRecipeDescription.setText(
                recipe.getDescription()
        );

        holder.itemView.setOnClickListener(
                v -> listener.onRecipeClick(recipe)
        );
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void filter(String searchText) {

        String query =
                searchText
                        .trim()
                        .toLowerCase(Locale.ROOT);

        recipes.clear();

        if (query.isEmpty()) {

            recipes.addAll(allRecipes);

        } else {

            for (Recipe recipe : allRecipes) {

                String name =
                        recipe.getName()
                                .toLowerCase(Locale.ROOT);

                String description =
                        recipe.getDescription()
                                .toLowerCase(Locale.ROOT);

                if (name.contains(query)
                        || description.contains(query)) {

                    recipes.add(recipe);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvRecipeDescription;

        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvRecipeName =
                    itemView.findViewById(
                            R.id.tvRecipeName
                    );

            tvRecipeDescription =
                    itemView.findViewById(
                            R.id.tvRecipeDescription
                    );
        }
    }
}