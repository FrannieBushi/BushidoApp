package com.example.bushidoapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bushidoapp.Habito;

@Database(entities = {Habito.class}, version = 1)
public abstract class HabitoDatabase extends RoomDatabase {
    public abstract HabitoDao HabitoDao();

}
