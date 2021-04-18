package com.example.cocktaildb2;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchListFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Cocktail> cocktails = new ArrayList<Cocktail>();
    EditText searchText;
    ProgressBar totalProgressBar;
    Call<Cocktails> cocktailAPICall;
    Spinner searchListFilter;

    public static int searchMode = 0;
    SearchPageViewModel searchPageViewModel;
    CocktailAdapter cocktailAdapter;
    String searchQuery;
    View view;
     public static RoomDB roomDatabase;



    public SearchListFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchListFrag newInstance(String param1, String param2) {
        SearchListFrag fragment = new SearchListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        this.view = view;
        searchText = view.findViewById(R.id.searchText);
        searchListFilter = view.findViewById(R.id.searchfilterDropDown);
        roomDatabase= Room.databaseBuilder(getContext(), RoomDB.class,"userdb").allowMainThreadQueries().build();
        searchText.setHint(searchMode==0?"Search by Name...":"Search by Ingredient...");
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchPageViewModel.cancelAPICall();
                totalProgressBar.setVisibility(View.VISIBLE);

                refreshLayout.setRefreshing(false);
                if(searchMode==0)
                   searchPageViewModel.APICallbyName(searchQuery);
                else
                   searchPageViewModel.APICallbyIngredient(searchQuery);
            }
        });

        searchListFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Search by Name"))
                    searchMode = 0;
                else
                    searchMode = 1;
                searchText.setHint(searchMode==0?"Search by Name...":"Search by Ingredient...");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        totalProgressBar = view.findViewById(R.id.totalProgressBar);
        totalProgressBar.setVisibility(View.VISIBLE);

        RecyclerView cocktailListView = view.findViewById(R.id.cocktailListView);
        cocktailListView.setLayoutManager(new LinearLayoutManager(getContext()));
        cocktailAdapter = new CocktailAdapter(cocktails, this);
        cocktailListView.setAdapter(cocktailAdapter);
        searchPageViewModel = ViewModelProviders.of(this).get(SearchPageViewModel.class);
        searchText.setText(searchPageViewModel.searchQuery);
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.hideActionBar();
        searchPageViewModel.getCocktails().observe(getViewLifecycleOwner(), new Observer<Cocktails>() {
            @Override
            public void onChanged(Cocktails _cocktails) {
                try {
                    List<Cocktail> __cocktails = _cocktails.getCocktails();
                    cocktails.clear();

                    for (int i = 0; i < __cocktails.size(); i++) {
                        cocktails.add(__cocktails.get(i));
                        totalProgressBar.setVisibility(View.INVISIBLE);

                    }

                    cocktailAdapter.notifyDataSetChanged();

                }catch(NullPointerException n){

                    totalProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        if(!searchPageViewModel.requested) {
            cocktails.clear();

            searchPageViewModel.APICallbyName("");
        }
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchQuery = charSequence.toString();
                totalProgressBar.setVisibility(View.VISIBLE);
                cocktails.clear();
                cocktailAdapter.notifyDataSetChanged();


                    if(searchMode==0) {
                        searchPageViewModel.cancelAPICall();
                        searchPageViewModel.APICallbyName(charSequence.toString());

                    }
                    else {
                        searchPageViewModel.cancelAPICall();
                        searchPageViewModel.APICallbyIngredient(charSequence.toString());
                    }

                }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch(direction) {
                    case ItemTouchHelper.RIGHT:
                        Cocktail cocktail = cocktails.get(viewHolder.getAdapterPosition());

                        CocktailFavData favData = new CocktailFavData();

                        favData.setName(cocktail.getName());
                        favData.setInstructions(cocktail.getInstructions());
                        favData.setCategory(cocktail.getCategory());
                        favData.setAlcoholic(cocktail.getAlchoholic());
                        favData.setIng1(cocktail.getIngredient1());
                        favData.setIng2(cocktail.getIngredient2());
                        favData.setIng3(cocktail.getIngredient3());
                        favData.setIng4(cocktail.getIngredient4());
                        favData.setIng5(cocktail.getIngredient5());
                        favData.setIng6(cocktail.getIngredient6());
                        favData.setIng7(cocktail.getIngredient7());
                        favData.setIng8(cocktail.getIngredient8());
                        favData.setIng9(cocktail.getIngredient9());
                        favData.setIng10(cocktail.getIngredient10());
                        favData.setIng11(cocktail.getIngredient11());
                        favData.setIng12(cocktail.getIngredient12());
                        favData.setIng13(cocktail.getIngredient13());
                        favData.setIng14(cocktail.getIngredient14());
                        favData.setIng15(cocktail.getIngredient15());

                        try {
                            printSnackbar("Cocktail Added to Favourites");
                            roomDatabase.mainDao().Add(favData);

                        } catch (SQLiteConstraintException s) {
                            printSnackbar("Cocktail already in favourites");
                        }

                        cocktailAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(cocktailListView);
        return view;
    }
    public void printSnackbar(String msg){
        ConstraintLayout layout = view.findViewById(R.id.parentt);
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG).show();
    }

}