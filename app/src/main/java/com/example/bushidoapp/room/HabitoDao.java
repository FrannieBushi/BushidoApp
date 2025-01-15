package com.example.bushidoapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bushidoapp.Habito;

import java.util.List;

@Dao
public interface HabitoDao {

    @Insert
    void insert(Habito habito);

    @Delete
    void delete(Habito habito);

    @Query("SELECT * FROM Habito")
    List<Habito> getAllHabitos();

    @Update
    void update(Habito habito);

}
