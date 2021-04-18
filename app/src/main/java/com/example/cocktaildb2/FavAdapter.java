package com.example.cocktaildb2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.CustomViewHolder> {
    List<Cocktail> cocktails;
    SearchListFrag searchListFrag;
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }
    public FavAdapter(List<Cocktail> _cocktails, SearchListFrag _searchListFrag){
        searchListFrag = _searchListFrag;
        cocktails = _cocktails;
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.cocktailNameView.setText(cocktails.get(position).getName());

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
