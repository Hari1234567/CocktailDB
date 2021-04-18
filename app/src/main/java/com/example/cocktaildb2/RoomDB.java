package com.example.cocktaildb2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CocktailFavData.class},version =1 ,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    public abstract MainDao mainDao();

}
