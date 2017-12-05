package com.example.ioana.budgetapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.budgetapplication.model.User;

import java.util.List;

/**
 * Created by Ioana on 05/12/2017.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> loadAll();

    @Insert
    void insert(User... user);

    @Update
    void update(User... user);

    @Delete
    void delete(User user);
}
