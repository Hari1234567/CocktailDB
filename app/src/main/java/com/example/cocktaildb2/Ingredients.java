package com.example.cocktaildb2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredients {
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
class Ingredient{
    @SerializedName("strDescription")
    private String description;

    public String getDescription() {
        return description;
    }
}