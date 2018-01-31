package com.example.ioana.gamestore.dao;
import android.arch.lifecycle.LiveData;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.gamestore.domain.Game;

import java.util.List;

/**
 * Room Dao - does all the main operations for the database
 */
@Dao
public interface GameDao {
    @Query("select * from games")
    LiveData<List<Game>> getAll();

    @Insert
    void add(Game game);

    @Insert
    void addAll(List<Game> games);

    @Delete
    void delete(Game game);

    @Update
    void update(Game game);

    @Query("delete from games")
    void deleteAll();
}
