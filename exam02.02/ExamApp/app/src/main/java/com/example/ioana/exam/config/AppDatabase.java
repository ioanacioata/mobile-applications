package com.example.ioana.exam.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ioana.exam.dao.SeatDao;
import com.example.ioana.exam.domain.Seat;

/**
 * Database configuration - contains only the Seat table
 * In the application are 4 databases :
 * 1. all games (client section) - corresponding to /games
 * 2. rented games (client section)
 * 3. bought games (client section)
 * 4. all games (employee section) - corresponding to /all
 */
@Database(entities = {Seat.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SeatDao getDao();
}
