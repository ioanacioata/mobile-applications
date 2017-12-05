package com.example.ioana.budgetapplication.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ioana.budgetapplication.dao.ProductDao;
import com.example.ioana.budgetapplication.dao.UserDao;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.User;

/**
 * Created by Ioana on 05/12/2017.
 */

@Database(entities = {User.class, Product.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProductDao productDao();
}