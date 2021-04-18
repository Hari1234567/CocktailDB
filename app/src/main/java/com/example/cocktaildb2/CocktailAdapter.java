package com.example.cocktaildb2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.List;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CustomViewHolder> {
    List<Cocktail> cocktails;
    SearchListFrag searchListFrag;


    public CocktailAdapter(List<Cocktail> _cocktails, SearchListFrag _searchListFrag){
        searchListFrag = _searchListFrag;
        cocktails = _cocktails;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_record, parent, false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.cocktailNameView.setText(cocktails.get(position).getName());
        holder.imgProgressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(cocktails.get(position).getImgURL()).into(holder.cocktailImageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.imgProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.imgProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        holder.cocktailParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPageViewModel searchPageViewModel  = ViewModelProviders.of(searchListFrag).get(SearchPageViewModel.class);
                SearchPageViewModel.clickedCocktail = cocktails.get(position);
               DetailsPopup detailsPopup = new DetailsPopup(cocktails.get(position));
               MainActivity mainActivity = (MainActivity)view.getContext();
               detailsPopup.show(mainActivity.getSupportFragmentManager(),"DETAILSPOPUP");

            }
        });

    }



    class CustomViewHolder extends RecyclerView.ViewHolder {

        public ImageView cocktailImageView;
        public TextView cocktailNameView;
        public ProgressBar imgProgressBar;
        public RelativeLayout cocktailParent;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            cocktailParent = itemView.findViewById(R.id.cocktailParent);
            cocktailImageView = itemView.findViewById(R.id.cocktailLogo);
            cocktailNameView = itemView.findViewById(R.id.cocktailName);
            imgProgressBar = itemView.findViewById(R.id.imgProgress);

        }
    }
}
