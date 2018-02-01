package com.example.ioana.exam.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ioana.exam.domain.Project;

import java.util.List;

/**
 * Room Dao - does all the main operations for the database
 */
@Dao
public interface ProjectDao {
    @Query("select * from Project")
    List<Project> getAll();

    @Insert
    void add(Project project);

    @Insert
    void addAll(List<Project> projects);

    @Delete
    void delete(Project project);

    @Update
    void update(Project project);

    @Query("delete from Project")
    void deleteAll();
}
