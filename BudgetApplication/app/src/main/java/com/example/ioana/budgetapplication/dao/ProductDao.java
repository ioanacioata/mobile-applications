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
    List<Product> loadAll();

    @Query("select * from product where name like :name ")
    Product findByName(String name);

    @Query("select * from product where name like :name and supermarketId = :supermarketId AND price = :price and brand like :brand")
    Product findProduct(String name, int supermarketId, Double price, String brand);

    @Insert
    void insert(Product... product);

    @Query("select count(*) from product where supermarketId = :supermarketId")
    Integer countProducts(int supermarketId);

    @Update
    void update(Product... product);


    @Delete
    void delete(Product... product);

}

