package com.example.mobile_final_project.utils;

import com.example.mobile_final_project.R;

public class categoryResources {

    int category_img[] = {R.drawable.categories_clothing, R.drawable.categories_education, R.drawable.categories_electronics,
            R.drawable.categories_health, R.drawable.categories_leisure, R.drawable.categories_food, R.drawable.categories_supermarket, R.drawable.categories_others, R.drawable.categories_others};

    int background_colors[] = {R.color.DeepSkyBlue, R.color.MediumOrchid, R.color.Yellow, R.color.light_green, R.color.orange, R.color.red, R.color.light_red, R.color.light_grey};

    String category_names[] = {"Clothing", "Education", "Electronics", "Health", "Leisure", "Food", "SuperMarket", "Others"};

    public Resource getValues(String category) {

        switch (category) {

            case "Clothing":
                return new Resource(category_img[0], background_colors[0]);

            case "Education":
                return new Resource(category_img[1], background_colors[1]);

            case "Electronics":
                return new Resource(category_img[2], background_colors[2]);

            case "Health":
                return new Resource(category_img[3], background_colors[3]);

            case "Leisure":
                return new Resource(category_img[4], background_colors[4]);

            case "Food":
                return new Resource(category_img[5], background_colors[5]);

            case "SuperMarket":
                return new Resource(category_img[6], background_colors[6]);

            case "Others":
                return new Resource(category_img[7], background_colors[7]);

        }
        return null;
    }
}
