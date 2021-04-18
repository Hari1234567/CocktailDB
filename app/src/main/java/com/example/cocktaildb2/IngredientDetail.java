package com.example.cocktaildb2;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientDetail extends DialogFragment {
    String ingredientName;
    public IngredientDetail(String _ingredientName) {
        ingredientName = _ingredientName;
    }
    public IngredientDetail(){ingredientName = SearchPageViewModel.clickedIngredient;}
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient,container,false);
        TextView title = view.findViewById(R.id.IngredientName);
        TextView body = view.findViewById(R.id.Ingredientsdescription);
        title.setText(ingredientName);
        body.setText("Please Wait while data is being collected...");
        body.setGravity(Gravity.CENTER);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create()).build();
        APIFetcher apiFetcher = retrofit.create(APIFetcher.class);
        Call<Ingredients> IngredientAPICall = apiFetcher.getIngredientbyName(ingredientName);
        IngredientAPICall.enqueue(new Callback<Ingredients>() {
            @Override
            public void onResponse(Call<Ingredients> call, Response<Ingredients> response) {
                if(response.isSuccessful()){
                    if(response.body().getIngredients().get(0).getDescription()!=null) {
                        body.setText(response.body().getIngredients().get(0).getDescription());
                        body.setGravity(Gravity.START);
                    }else{
                        body.setText("No Data available");
                    }
                }else{
                    body.setText("Data Collection Failed, Please reload and try again");
                }
            }

            @Override
            public void onFailure(Call<Ingredients> call, Throwable t) {
                body.setText("Data Collection Failed, Please reload and try again");
            }
        });
        return view;
    }


}
