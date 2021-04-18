package com.example.cocktaildb2;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Query("select * from  Cocktails")
    public List<CocktailFavData> getFavData();
    @Insert
    void Add(CocktailFavData favData);

    @Delete
    void delete(CocktailFavData favData);

}
