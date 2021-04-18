package com.example.cocktaildb2;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    Target target;
    ImageView cocktailLogo;
    HomePageViewModel homePageViewModel;
    TextView Instructions,category,alcoholic;
    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        cocktailLogo = view.findViewById(R.id.cocktailLogo);
        this.view = view;
        ProgressBar loadingBar  = view.findViewById(R.id.loadingBar);
        loadingBar.setVisibility(View.VISIBLE);

        MainActivity parentActivity = (MainActivity)getActivity();
        final TextView cocktailLabel = view.findViewById(R.id.cocktailName);
        Instructions = view.findViewById(R.id.instructions);
        category = view.findViewById(R.id.category);
        alcoholic = view.findViewById(R.id.alcoholic);
        loadingBar.setVisibility(View.VISIBLE);
        homePageViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
        homePageViewModel.getRandomCocktailsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Cocktails>() {
            @Override
            public void onChanged(Cocktails cocktails) {
                try {
                    Cocktail randomCockTails = cocktails.getCocktails().get(0);
                    cocktailLabel.setText(randomCockTails.getName());
                    Instructions.setText(randomCockTails.getInstructions());
                    category.setText(randomCockTails.getCategory());
                    alcoholic.setText(randomCockTails.getAlchoholic());
                }catch(NullPointerException n){
                    printSnackbar("Couldn't Fetch Data, Please Reload");
                }
                }
        });
        homePageViewModel.getRandomImage().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {

                cocktailLogo.setImageBitmap(bitmap);
                loadingBar.setVisibility(View.INVISIBLE);
            }
        });
        if(!homePageViewModel.requested)
        homePageViewModel.RandomAPICall();

        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePageViewModel.cancelAPICall();
                loadingBar.setVisibility(View.VISIBLE);
                Instructions.setText(getResources().getText(R.string.result_fetch));
                category.setText(getResources().getText(R.string.result_fetch));
                alcoholic.setText(getResources().getText(R.string.result_fetch));
                cocktailLabel.setText("");
                cocktailLogo.setImageBitmap(null);
                refreshLayout.setRefreshing(false);

                homePageViewModel.requested = false;
                homePageViewModel.RandomAPICall();
            }
        });


        return view;
    }
    public void printSnackbar(String msg){
        RelativeLayout layout = view.findViewById(R.id.parentt);
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG).show();
    }

}