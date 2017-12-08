package com.example.ioana.budgetapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.Supermarket;

import java.util.List;

/**
 * Created by Ioana on 06/12/2017.
 */

@Dao
public interface SupermarketDao {
    @Query("select * from supermarket")
    public List<Supermarket> loadAll();

    @Query("select * from supermarket where id = :id LIMIT 1")
    public Supermarket findById(int id);

    @Query("select * from supermarket where name like :name")
    public Supermarket findByName(String name);

    @Query("select name from supermarket")
    public List<String> getAllNames();

    @Query("select * from supermarket where name like :name and address like :address")
    public Supermarket getSupermarket(String name, String address);

    @Insert
    public void insert(Supermarket... supermarkets);

    @Update
    public void update(Supermarket... supermarkets);


    @Delete
    public void delete(Supermarket... supermarkets);
}
