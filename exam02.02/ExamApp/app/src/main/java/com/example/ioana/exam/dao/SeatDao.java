package com.example.ioana.exam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.exam.domain.Seat;

import java.util.List;

/**
 * Room Dao - does all the main operations for the database
 */
@Dao
public interface SeatDao {
    @Query("select * from Seat")
    List<Seat> getAll();

    @Insert
    void add(Seat seat);

    @Insert
    void addAll(List<Seat> seats);

    @Delete
    void delete(Seat seat);

    @Update
    void update(Seat seat);

    @Query("delete from Seat")
    void deleteAll();
}
