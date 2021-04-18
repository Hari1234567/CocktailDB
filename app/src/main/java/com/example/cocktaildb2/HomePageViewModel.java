package com.example.cocktaildb2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageViewModel extends ViewModel {
    MutableLiveData<Cocktails> randomCocktailsMutableLiveData;
    MutableLiveData<Bitmap> imgLogo;
    Call<Cocktails> cocktailAPICall;
    public boolean requested;
    public MutableLiveData<Cocktails> getRandomCocktailsMutableLiveData(){
        return randomCocktailsMutableLiveData;
    }
    public MutableLiveData<Bitmap> getRandomImage(){
        return imgLogo;
    }
    public HomePageViewModel(){
        requested = false;
        randomCocktailsMutableLiveData = new MutableLiveData<Cocktails>();
        imgLogo = new MutableLiveData<Bitmap>();
    }
    public void cancelAPICall(){
        cocktailAPICall.cancel();
    }

    public void RandomAPICall(){
        requested = true;
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create()).build();
        APIFetcher apiFetcher = retrofit.create(APIFetcher.class);
        cocktailAPICall = apiFetcher.getRandomCocktails();
        cocktailAPICall.enqueue(new Callback<Cocktails>() {
            @Override
            public void onResponse(Call<Cocktails> call, Response<Cocktails> response) {
                if(response.isSuccessful()){
                    randomCocktailsMutableLiveData.postValue(response.body());
                    Picasso.get().load(response.body().getCocktails().get(0).getImgURL()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                imgLogo.postValue(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                imgLogo.postValue(null);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                              //  imgLogo.postValue(null);
                        }
                    });
                }else{
                    randomCocktailsMutableLiveData.postValue(null);
                    Log.i("TEST",response.message());
                }

            }

            @Override
            public void onFailure(Call<Cocktails> call, Throwable t) {
                randomCocktailsMutableLiveData.postValue(null);
                Log.i("TEST",t.getLocalizedMessage());
            }
        });

    }
}
