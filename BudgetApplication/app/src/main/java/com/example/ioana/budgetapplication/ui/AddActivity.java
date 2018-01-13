package com.example.ioana.budgetapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.Shop;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    public static final String TAG = AddActivity.class.getName();
    EditText nameEditText;
    EditText priceEditText;
    EditText brandEditText;
    Spinner supermarketSpinner;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = findViewById(R.id.nameToAddEditText);
        priceEditText = findViewById(R.id.priceToAddEditText);
        brandEditText = findViewById(R.id.brandToAddEditText);
        supermarketSpinner = findViewById(R.id.supermarketToAddSpinner);

        imageView = findViewById(R.id.addProductImageView);
        //set content for image
        imageView.setImageResource(R.drawable.noimage);

        //populate spinner
        List<Shop> shopList = Arrays.asList(Shop.values());
        List<String> spinnerList = new ArrayList<>();
        for (Shop s : shopList) {
            spinnerList.add(s.toString());
        }
        Log.i(TAG, spinnerList.get(0) + " is first elem and size is " + spinnerList.size());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        supermarketSpinner.setAdapter(arrayAdapter);

        //add actions to button SAVE
        Button saveButton = (Button) findViewById(R.id.saveButtonAddWindow);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameEditText.getText());
                Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
                String brand = String.valueOf(brandEditText.getText());
                Shop shop = Shop.valueOf(supermarketSpinner.getSelectedItem().toString());

                Product resultProd = new Product(name, price, shop, brand, R.drawable.noimage);
                //todo insert product
                //db.productDao().insert(resultProd);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("products");
                String id = ref.push().getKey();
                resultProd.setId(id);
                ref.child(id).setValue(resultProd);


                Log.i(TAG, "name: " + resultProd.getName());
                Intent intent = new Intent(AddActivity.this, MainActivity.class);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
