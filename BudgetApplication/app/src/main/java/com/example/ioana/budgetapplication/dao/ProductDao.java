package com.example.ioana.budgetapplication.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.budgetapplication.model.Product;

import java.util.List;

/**
 * Created by Ioana on 05/12/2017.
 */

@Dao
public interface ProductDao {
    @Query("select * from product")
    public List<Product> loadAll();

    @Query("select * from product where name like :name")
    public Product findByName(String name);

    @Insert
    public void insert(Product... product);

    @Update
    public void update(Product... product);


    @Delete
    public void delete(Product... product);

}

