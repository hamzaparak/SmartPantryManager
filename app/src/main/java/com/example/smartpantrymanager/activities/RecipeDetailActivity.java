package com.example.smartpantrymanager.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity
        extends AppCompatActivity {

    private TextView recipeTitle;
    private TextView ingredientsText;
    private TextView methodText;

    private DatabaseHelper databaseHelper;

    private int recipeId = -1;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        recipeTitle =
                findViewById(
                        R.id.recipeTitle
                );

        ingredientsText =
                findViewById(
                        R.id.ingredientsText
                );

        methodText =
                findViewById(
                        R.id.methodText
                );

        Button backButton =
                findViewById(
                        R.id.backButton
                );

        databaseHelper =
                new DatabaseHelper(this);

        recipeId =
                getIntent()
                        .getIntExtra(
                                "RECIPE_ID",
                                -1
                        );

        backButton.setOnClickListener(v ->
                finish()
        );

        if (recipeId == -1) {
            finish();
            return;
        }

        loadRecipe();
    }

    private void loadRecipe() {

        Recipe recipe =
                databaseHelper
                        .getRecipeById(
                                recipeId
                        );

        if (recipe == null) {
            finish();
            return;
        }

        recipeTitle.setText(
                recipe.getName()
        );

        List<RecipeIngredient> ingredients =
                databaseHelper
                        .getRecipeIngredients(
                                recipeId
                        );

        StringBuilder ingredientList =
                new StringBuilder();

        for (RecipeIngredient ingredient :
                ingredients) {

            ingredientList
                    .append(
                            formatQuantity(
                                    ingredient
                                            .getRequiredQuantity()
                            )
                    )
                    .append(" ")
                    .append(
                            ingredient
                                    .getUnit()
                    )
                    .append(" ")
                    .append(
                            ingredient
                                    .getIngredientName()
                    )
                    .append("\n");
        }

        ingredientsText.setText(
                ingredientList.toString()
        );

        methodText.setText(
                recipe.getMethod()
        );
    }

    private String formatQuantity(
            double quantity) {

        if (quantity ==
                (long) quantity) {

            return String.valueOf(
                    (long) quantity
            );
        }

        return String.valueOf(
                quantity
        );
    }
}