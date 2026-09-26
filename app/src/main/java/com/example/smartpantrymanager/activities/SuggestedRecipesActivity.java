package com.example.smartpantrymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapters.RecipeAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.utils.RecipeMatcher;

import java.util.List;

public class SuggestedRecipesActivity
        extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;

    private TextView noRecipesTitle;
    private TextView noRecipesMessage;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_suggested_recipes
        );

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat
                                            .Type
                                            .systemBars()
                            );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        recipeRecyclerView =
                findViewById(
                        R.id.recipeRecyclerView
                );

        noRecipesTitle =
                findViewById(
                        R.id.noRecipesTitle
                );

        noRecipesMessage =
                findViewById(
                        R.id.noRecipesMessage
                );

        Button backButton =
                findViewById(
                        R.id.backButton
                );

        databaseHelper =
                new DatabaseHelper(this);

        recipeRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        backButton.setOnClickListener(v ->
                finish()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<PantryItem> pantryItems =
                databaseHelper
                        .getAllPantryItems();

        List<Recipe> allRecipes =
                databaseHelper
                        .getAllRecipes();

        List<Recipe> suggestedRecipes =
                RecipeMatcher.getSuggestedRecipes(
                        pantryItems,
                        allRecipes,
                        recipeId ->
                                databaseHelper
                                        .getRecipeIngredients(
                                                recipeId
                                        )
                );

        RecipeAdapter adapter =
                new RecipeAdapter(
                        suggestedRecipes
                );

        recipeRecyclerView.setAdapter(
                adapter
        );

        if (suggestedRecipes.isEmpty()) {

            recipeRecyclerView.setVisibility(
                    View.GONE
            );

            noRecipesTitle.setVisibility(
                    View.VISIBLE
            );

            noRecipesMessage.setVisibility(
                    View.VISIBLE
            );

        } else {

            recipeRecyclerView.setVisibility(
                    View.VISIBLE
            );

            noRecipesTitle.setVisibility(
                    View.GONE
            );

            noRecipesMessage.setVisibility(
                    View.GONE
            );
        }
    }
}