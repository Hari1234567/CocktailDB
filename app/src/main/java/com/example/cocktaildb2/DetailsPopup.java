package com.example.cocktaildb2;

import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsPopup extends DialogFragment {
    Cocktail cocktail;
    ImageView cocktailLogo;
    TextView cockTailName,Category,Alcoholic,Instruction;
    View view;
    LinearLayout ingredientsLayout;
    public DetailsPopup(){
        cocktail = SearchPageViewModel.clickedCocktail;

    }
    public DetailsPopup(Cocktail _cockTail) {
        cocktail = _cockTail;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deatils_popup,container,false);
        this.view = view;

        cocktailLogo = view.findViewById(R.id.cocktailLogo);
        cockTailName = view.findViewById(R.id.cocktailName);
        Category = view.findViewById(R.id.category);
        Alcoholic = view.findViewById(R.id.alcoholic);
        Instruction = view.findViewById(R.id.instructions);
        ingredientsLayout = view.findViewById(R.id.ingredientsLayout);

        Picasso.get().load(cocktail.getImgURL()).into(cocktailLogo);
        cockTailName.setText(cocktail.getName());
        if(SearchListFrag.searchMode==1){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").addConverterFactory(GsonConverterFactory.create()).build();
            APIFetcher apiFetcher = retrofit.create(APIFetcher.class);
            Call<Cocktails> cocktailAPICall;
            cocktailAPICall = apiFetcher.getCocktailbyName(cocktail.getName());
            cocktailAPICall.enqueue(new Callback<Cocktails>() {
                @Override
                public void onResponse(Call<Cocktails> call, Response<Cocktails> response) {
                    if(response.isSuccessful()){
                        cocktail = response.body().getCocktails().get(0);
                        Category.setText(cocktail.getCategory());
                        Alcoholic.setText(cocktail.getAlchoholic());
                        Instruction.setText(cocktail.getInstructions());


                        int lastIngredient = 0;

                        for(int i = 0;i<15;i++){
                            CardView cardView = (CardView)ingredientsLayout.getChildAt(i);
                            TextView ingredientText =  (TextView)cardView.getChildAt(0);
                            String ingredient;


                            ingredient = cocktail.getIngredient(i);

                            ingredientText.setText(ingredient);
                            String finalIngredient = ingredient;
                            ingredientText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    IngredientDetail ingredientDetail = new IngredientDetail(finalIngredient);
                                    MainActivity mainActivity = (MainActivity)getContext();
                                    ingredientDetail.show(mainActivity.getSupportFragmentManager(),"INGREDIENT");
                                }
                            });
                            if(ingredient==null){
                                lastIngredient = i;
                                break;
                            }
                        }

                        for(int i = 14;i>=lastIngredient;i--){
                            ingredientsLayout.removeView(ingredientsLayout.getChildAt(i));
                        }

                    }else{
                        printSnackbar("Couldn't Fetch Data, Please Reload");
                        Category.setText("Couldn't Fetch Data");
                        Instruction.setText("Couldn't Fetch Data");
                        Alcoholic.setText("Couldn't Fetch Data");
                        for(int i = 14;i>=0;i--){
                            ingredientsLayout.removeView(ingredientsLayout.getChildAt(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Cocktails> call, Throwable t) {
                    printSnackbar("Couldn't Fetch Data, Please Reload");
                        Category.setText("Couldn't Fetch Data");
                        Instruction.setText("Couldn't Fetch Data");
                        Alcoholic.setText("Couldn't Fetch Data");

                    for(int i = 14;i>=0;i--){
                        ingredientsLayout.removeView(ingredientsLayout.getChildAt(i));
                    }
                }
            });
        }else {
            Category.setText(cocktail.getCategory());
            Alcoholic.setText(cocktail.getAlchoholic());
            Instruction.setText(cocktail.getInstructions());

            ingredientsLayout = view.findViewById(R.id.ingredientsLayout);
            int lastIngredient = 0;

            for (int i = 0; i < 15; i++) {
                CardView cardView = (CardView) ingredientsLayout.getChildAt(i);
                TextView ingredientText = (TextView) cardView.getChildAt(0);
                String ingredient;


                ingredient = cocktail.getIngredient(i);

                ingredientText.setText(ingredient);
                String finalIngredient = ingredient;
                ingredientText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SearchPageViewModel.clickedIngredient = finalIngredient;

                        IngredientDetail ingredientDetail = new IngredientDetail(finalIngredient);
                        MainActivity mainActivity = (MainActivity) getContext();
                        ingredientDetail.show(mainActivity.getSupportFragmentManager(), "INGREDIENT");
                    }
                });
                if (ingredient == null) {
                    lastIngredient = i;
                    break;
                }


            }

            for (int i = 14; i >= lastIngredient; i--) {
                ingredientsLayout.removeView(ingredientsLayout.getChildAt(i));
            }
        }







        return view;
    }
    public void printSnackbar(String msg){
        RelativeLayout layout = view.findViewById(R.id.parentt);
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG).show();
    }



}
