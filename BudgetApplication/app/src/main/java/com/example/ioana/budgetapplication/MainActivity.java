package com.example.ioana.budgetapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    public static final int REQUEST_CODE = 1;
    List<Product> products = new ArrayList<>();
    ListView listView;
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeList();

        Log.e(TAG, "IN ON CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.w(TAG, "IN ON CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.i(TAG, "IN ON CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.d(TAG, "IN ON CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        Log.v(TAG, "IN ON CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        listView = (ListView) findViewById(R.id.productList);
        //display the list
         productListAdapter = new ProductListAdapter(products, getLayoutInflater());
        listView.setAdapter(productListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("product",products.get(position));
                intent.putExtra("position",position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){

                Product p = (Product) data.getSerializableExtra("resultProduct");
                Integer pos = (Integer) data.getSerializableExtra("resultPosition");
                products.set(pos, p);

                ProductListAdapter productListAdapter = new ProductListAdapter(products, getLayoutInflater());
                listView.setAdapter(productListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent= new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("product",products.get(position));
                        intent.putExtra("position",position);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }
        }
    }
    @Override
    protected  void onResume(){
        super.onResume();
        productListAdapter.notifyDataSetChanged();

    }

    private void initializeList() {
        products.add(new Product("Coca-Cola 0.5l", 2.5, "Auchan Iulius Mall", "Coca-Cola", R.drawable.cola5));
        products.add(new Product("Lapte 1l", 4.0, "Auchan Iulius Mall", "Zuzu", R.drawable.zuzu1l));
        products.add(new Product("Cafea 200 grame", 12.0, "Auchan Iulius Mall", "Nescafe",R.drawable.nescafe));
    }

}
