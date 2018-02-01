package com.example.ioana.exam.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ioana.exam.dao.ProjectDao;
import com.example.ioana.exam.domain.Project;

/**
 * Database configuration - contains only the Project table
 * In the application are 4 databases :
 * 1. all games (client section) - corresponding to /games
 * 2. rented games (client section)
 * 3. bought games (client section)
 * 4. all games (employee section) - corresponding to /all
 */
@Database(entities = {Project.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProjectDao getDao();
}
