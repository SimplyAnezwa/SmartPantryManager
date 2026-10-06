package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    /**
     * Checks whether a recipe can be prepared using the
     * ingredients currently available in the pantry.
     *
     * A recipe is available only when:
     * 1. Every required ingredient exists in the pantry.
     * 2. The pantry quantity is sufficient.
     * 3. The units are compatible.
     */
    public static boolean isRecipeAvailable(
            List<RecipeIngredient> requiredIngredients,
            List<PantryItem> pantryItems) {

        if (requiredIngredients == null || requiredIngredients.isEmpty()) {
            return false;
        }

        if (pantryItems == null || pantryItems.isEmpty()) {
            return false;
        }

        for (RecipeIngredient required : requiredIngredients) {

            boolean ingredientAvailable = false;

            String requiredName =
                    normalizeIngredientName(required.getIngredientName());

            for (PantryItem pantryItem : pantryItems) {

                String pantryName =
                        normalizeIngredientName(pantryItem.getName());

                if (requiredName.equals(pantryName)) {

                    if (isUnitCompatible(
                            required.getUnit(),
                            pantryItem.getUnit())) {

                        double pantryQuantity =
                                convertToBaseQuantity(
                                        pantryItem.getQuantity(),
                                        pantryItem.getUnit());

                        double requiredQuantity =
                                convertToBaseQuantity(
                                        required.getRequiredQuantity(),
                                        required.getUnit());

                        if (pantryQuantity >= requiredQuantity) {
                            ingredientAvailable = true;
                            break;
                        }
                    }
                }
            }

            /*
             * If even one required ingredient is missing
             * or insufficient, the entire recipe fails.
             */
            if (!ingredientAvailable) {
                return false;
            }
        }

        return true;
    }

    /**
     * Normalises ingredient names so that common singular/plural
     * differences do not prevent recipe matching.
     */
    public static String normalizeIngredientName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = name
                .trim()
                .toLowerCase(Locale.ROOT);

        if (normalized.endsWith("ies")) {
            normalized = normalized.substring(
                    0,
                    normalized.length() - 3
            ) + "y";

        } else if (normalized.endsWith("oes")) {
            normalized = normalized.substring(
                    0,
                    normalized.length() - 2
            );

        } else if (normalized.endsWith("es")
                && normalized.length() > 3) {

            normalized = normalized.substring(
                    0,
                    normalized.length() - 2
            );

        } else if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 2) {

            normalized = normalized.substring(
                    0,
                    normalized.length() - 1
            );
        }

        return normalized;
    }

    /**
     * Checks whether two units can be compared.
     */
    private static boolean isUnitCompatible(
            String requiredUnit,
            String pantryUnit) {

        String required = normalizeUnit(requiredUnit);
        String pantry = normalizeUnit(pantryUnit);

        if (required.equals(pantry)) {
            return true;
        }

        // Weight units
        if (isWeightUnit(required)
                && isWeightUnit(pantry)) {
            return true;
        }

        // Volume units
        if (isVolumeUnit(required)
                && isVolumeUnit(pantry)) {
            return true;
        }

        return false;
    }

    /**
     * Converts compatible quantities into a common base unit.
     *
     * Weight -> grams
     * Volume -> millilitres
     * Other units remain unchanged.
     */
    private static double convertToBaseQuantity(
            double quantity,
            String unit) {

        String normalizedUnit = normalizeUnit(unit);

        switch (normalizedUnit) {

            case "kg":
                return quantity * 1000;

            case "g":
                return quantity;

            case "l":
                return quantity * 1000;

            case "ml":
                return quantity;

            default:
                return quantity;
        }
    }

    private static String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized = unit
                .trim()
                .toLowerCase(Locale.ROOT);

        switch (normalized) {

            case "grams":
            case "gram":
                return "g";

            case "kilograms":
            case "kilogram":
                return "kg";

            case "millilitres":
            case "milliliters":
            case "millilitre":
            case "milliliter":
                return "ml";

            case "litres":
            case "liters":
            case "litre":
            case "liter":
                return "l";

            case "pieces":
            case "piece":
                return "piece";

            case "slices":
            case "slice":
                return "slice";

            case "cans":
            case "can":
                return "can";

            case "leaves":
            case "leaf":
                return "leaf";

            default:
                return normalized;
        }
    }

    private static boolean isWeightUnit(String unit) {
        return unit.equals("g") || unit.equals("kg");
    }

    private static boolean isVolumeUnit(String unit) {
        return unit.equals("ml") || unit.equals("l");
    }
}