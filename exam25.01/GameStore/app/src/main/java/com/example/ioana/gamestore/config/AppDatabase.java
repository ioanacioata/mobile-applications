package com.example.ioana.gamestore.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ioana.gamestore.dao.GameDao;
import com.example.ioana.gamestore.domain.Game;

/**
 * Database configuration - contains only the Game table
 * In the application are 4 databases :
 * 1. all games (client section) - corresponding to /games
 * 2. rented games (client section)
 * 3. bought games (client section)
 * 4. all games (employee section) - corresponding to /all
 */
@Database(entities = {Game.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao getDao();
}
