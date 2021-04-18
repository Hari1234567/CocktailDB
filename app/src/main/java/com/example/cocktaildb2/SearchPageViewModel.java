package com.example.cocktaildb2;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPageViewModel extends ViewModel {
    MutableLiveData<Cocktails> cocktails;

    public boolean requested = false;
    Call<Cocktails> cocktailAPICall;
    public String searchQuery = "";
    public static Cocktail clickedCocktail = null;
    public static String clickedIngredient = null;
    public int searchMode = 0;

    public SearchPageViewModel() {
        cocktails = new MutableLiveData<Cocktails>();

    }
    public void cancelAPICall(){
        cocktailAPICall.cancel();
    }


    public MutableLiveData<Cocktails> getCocktails(){
        return cocktails;
    }





    public void APICallbyIngredient(String target) {
        requested = true;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create()).build();
        APIFetcher apiFetcher = retrofit.create(APIFetcher.class);
        cocktailAPICall = apiFetcher.getCocktailbyIngredient(target);
        cocktailAPICall.enqueue(new Callback<Cocktails>() {
            @Override
            public void onResponse(Call<Cocktails> call, Response<Cocktails> response) {
                if (response.isSuccessful()) {
                    cocktails.postValue(response.body());

                } else {
                    cocktails.postValue(null);
                    Log.i("TEST", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cocktails> call, Throwable t) {
                cocktails.postValue(null);
                Log.i("TEST",t.getLocalizedMessage());
            }
        });
    }
    public void APICallbyName(String target) {
        requested = true;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create()).build();
        APIFetcher apiFetcher = retrofit.create(APIFetcher.class);
        cocktailAPICall = apiFetcher.getCocktailbyName(target);
        cocktailAPICall.enqueue(new Callback<Cocktails>() {
            @Override
            public void onResponse(Call<Cocktails> call, Response<Cocktails> response) {
                if (response.isSuccessful()) {
                    cocktails.postValue(response.body());

                } else {
                    cocktails.postValue(null);
                    Log.i("TEST", response.message());
                }
            }

            @Override
            public void onFailure(Call<Cocktails> call, Throwable t) {
                cocktails.postValue(null);
                Log.i("TEST",t.getLocalizedMessage());
            }
        });
    }

}
