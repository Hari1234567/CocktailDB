package com.example.cocktaildb2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIFetcher {
    @GET("random.php")
    Call<Cocktails> getRandomCocktails();

    @GET("search.php")
    Call<Cocktails> getCocktailbyName(@Query("s")String f);

    @GET("filter.php")
    Call<Cocktails> getCocktailbyIngredient(@Query("i")String i);

    @GET("search.php")
    Call<Ingredients> getIngredientbyName(@Query("i")String s);
}
