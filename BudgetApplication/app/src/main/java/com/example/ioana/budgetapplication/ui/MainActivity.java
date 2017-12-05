package com.example.ioana.budgetapplication.ui;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ioana.budgetapplication.ui.adapter.ProductListAdapter;
import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.config.AppDatabase;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    public static final int REQUEST_CODE = 1;
    //    List<Product> products = new ArrayList<>();
    ListView listView;
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeList();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final List<Product> products = db.productDao().loadAll();

//        db.userDao().insert(new User(1, "Ioana", "ioana@gmail.com", "126i"));
//        Log.i(TAG, String.valueOf(db.userDao().loadAll().size()));

        listView = (ListView) findViewById(R.id.productList);
        //display the list
        productListAdapter = new ProductListAdapter(products, getLayoutInflater());
        listView.setAdapter(productListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("product", products.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                Product p = (Product) data.getSerializableExtra("resultProduct");
//                Integer pos = (Integer) data.getSerializableExtra("resultPosition");
//                products.set(pos, p);
                final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                final List<Product> products = db.productDao().loadAll();

                ProductListAdapter productListAdapter = new ProductListAdapter(products, getLayoutInflater());
                listView.setAdapter(productListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("product", products.get(position));
                        intent.putExtra("position", position);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        productListAdapter.notifyDataSetChanged();

    }

    private void initializeList() {
//        Product p1 = new Product("Coca-Cola 0.5l", 2.5, "Auchan Iulius Mall", "Coca-Cola", R.drawable.cola5);
//        Product p2 = new Product("Lapte 1l", 4.0, "Auchan Iulius Mall", "Zuzu", R.drawable.zuzu1l);
//        Product p3 = new Product("Cafea 200 grame", 12.0, "Auchan Iulius Mall", "Nescafe", R.drawable.nescafe);
//        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
//        db.productDao().insert(p1);
//        db.productDao().insert(p2);
//        db.productDao().insert(p3);

    }

}
