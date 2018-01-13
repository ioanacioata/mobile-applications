package com.example.ioana.budgetapplication.config;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ioana on 12/01/2018.
 */

public class MyDatabase {
    public static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase() {
        if(database==null){
            database=FirebaseDatabase.getInstance();
//            database.setPersistenceEnabled(true); //offline data
        }
        return database;
    }
}
