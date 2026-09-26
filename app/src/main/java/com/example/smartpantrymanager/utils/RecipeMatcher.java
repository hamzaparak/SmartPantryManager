package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    public interface RecipeIngredientProvider {

        List<RecipeIngredient> getIngredients(
                int recipeId
        );
    }

    public static List<Recipe> getSuggestedRecipes(
            List<PantryItem> pantryItems,
            List<Recipe> recipes,
            RecipeIngredientProvider provider) {

        List<Recipe> suggestedRecipes =
                new ArrayList<>();

        for (Recipe recipe : recipes) {

            List<RecipeIngredient> requiredIngredients =
                    provider.getIngredients(
                            recipe.getId()
                    );

            if (canMakeRecipe(
                    pantryItems,
                    requiredIngredients)) {

                suggestedRecipes.add(recipe);
            }
        }

        return suggestedRecipes;
    }

    public static boolean canMakeRecipe(
            List<PantryItem> pantryItems,
            List<RecipeIngredient> requiredIngredients) {

        for (RecipeIngredient required :
                requiredIngredients) {

            double availableQuantity =
                    getAvailableQuantity(
                            pantryItems,
                            required.getIngredientName(),
                            required.getUnit()
                    );

            if (availableQuantity <
                    required.getRequiredQuantity()) {

                return false;
            }
        }

        return true;
    }

    private static double getAvailableQuantity(
            List<PantryItem> pantryItems,
            String requiredName,
            String requiredUnit) {

        double totalQuantity = 0;

        String normalRequiredName =
                normaliseName(requiredName);

        for (PantryItem pantryItem :
                pantryItems) {

            String normalPantryName =
                    normaliseName(
                            pantryItem.getName()
                    );

            if (!normalRequiredName.equals(
                    normalPantryName)) {

                continue;
            }

            double convertedQuantity =
                    convertQuantity(
                            pantryItem.getQuantity(),
                            pantryItem.getUnit(),
                            requiredUnit
                    );

            if (convertedQuantity >= 0) {

                totalQuantity +=
                        convertedQuantity;
            }
        }

        return totalQuantity;
    }

    private static String normaliseName(
            String name) {

        if (name == null) {
            return "";
        }

        String normalName =
                name.trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        if (normalName.equals("tomatoes")) {
            return "tomato";
        }

        if (normalName.equals("potatoes")) {
            return "potato";
        }

        if (normalName.equals("oats")) {
            return "oats";
        }

        if (normalName.endsWith("s") &&
                !normalName.endsWith("ss")) {

            normalName =
                    normalName.substring(
                            0,
                            normalName.length() - 1
                    );
        }

        return normalName;
    }

    private static double convertQuantity(
            double quantity,
            String fromUnit,
            String toUnit) {

        if (fromUnit == null ||
                toUnit == null) {

            return -1;
        }

        if (fromUnit.equalsIgnoreCase(
                toUnit)) {

            return quantity;
        }

        if (fromUnit.equalsIgnoreCase("kg") &&
                toUnit.equalsIgnoreCase("g")) {

            return quantity * 1000;
        }

        if (fromUnit.equalsIgnoreCase("g") &&
                toUnit.equalsIgnoreCase("kg")) {

            return quantity / 1000;
        }

        if (fromUnit.equalsIgnoreCase("L") &&
                toUnit.equalsIgnoreCase("ml")) {

            return quantity * 1000;
        }

        if (fromUnit.equalsIgnoreCase("ml") &&
                toUnit.equalsIgnoreCase("L")) {

            return quantity / 1000;
        }

        return -1;
    }
}