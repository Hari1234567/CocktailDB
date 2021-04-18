package com.example.cocktaildb2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cocktails {
     @SerializedName("drinks")
     private List<Cocktail> cocktails;

    public List<Cocktail> getCocktails() {
        return cocktails;
    }
}

class Cocktail{
    @SerializedName("strDrink")
    private String Name;
    @SerializedName("idDrink")
    private String ID;
    @SerializedName("strInstructions")
    private String instructions;
    @SerializedName("strCategory")
    private String category;
    @SerializedName("strAlcoholic")
    private String alchoholic;
    @SerializedName("strDrinkThumb")
    private String imgURL;
    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strIngredient11")
    private String ingredient11;
    @SerializedName("strIngredient12")
    private String ingredient12;
    @SerializedName("strIngredient13")
    private String ingredient13;
    @SerializedName("strIngredient14")
    private String ingredient14;
    @SerializedName("strIngredient15")
    private String ingredient15;

    public String getInstructions() {
        return instructions;
    }

    public String getCategory() {
        return category;
    }

    public String getAlchoholic() {
        return alchoholic;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public String getIngredient5() {
        return ingredient5;
    }

    public String getIngredient6() {
        return ingredient6;
    }

    public String getIngredient7() {
        return ingredient7;
    }

    public String getIngredient8() {
        return ingredient8;
    }

    public String getIngredient9() {
        return ingredient9;
    }

    public String getIngredient10() {
        return ingredient10;
    }

    public String getIngredient11() {
        return ingredient11;
    }

    public String getIngredient12() {
        return ingredient12;
    }

    public String getIngredient13() {
        return ingredient13;
    }

    public String getIngredient14() {
        return ingredient14;
    }

    public String getIngredient15() {
        return ingredient15;
    }

    public String getIngredient(int index){
        switch(index){
            case 0:
                return ingredient1;

            case 1:
                return ingredient2;

            case 2:
                return ingredient3;

            case 3:
                return ingredient4;

            case 4:
                return ingredient5;

            case 5:
                return ingredient6;

            case 6:
                return ingredient7;

            case 7:
                return ingredient8;

            case 8:
                return ingredient9;

            case 9:
                return ingredient10;

            case 10:
                return ingredient11;

            case 11:
                return ingredient12;

            case 12:
                return ingredient13;

            case 13:
                return ingredient14;

            case 14:
                return ingredient15;


        }
        return null;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }
}
