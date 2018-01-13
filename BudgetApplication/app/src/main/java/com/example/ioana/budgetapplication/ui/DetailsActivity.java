package com.example.ioana.budgetapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.Shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements Button.OnClickListener {
    public static final String TAG = DetailsActivity.class.getName();
    EditText nameEditText;
    EditText priceEditText;
    EditText brandEditText;
    Spinner shopSpinner;
    ImageView imageView;
    Product currentProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        currentProduct = (Product) getIntent().getSerializableExtra("product");
        Log.i(TAG, "PROD ID supermarket : " + currentProduct.toString());
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        brandEditText = findViewById(R.id.BrandEditText);
        shopSpinner = findViewById(R.id.supermarketSpinner);
        imageView = findViewById(R.id.detailsProductImageView);

        //set text to show
        nameEditText.setText(currentProduct.getName());
        priceEditText.setText(currentProduct.getPrice().toString());
        brandEditText.setText(currentProduct.getBrand());
        populateShopSpinner();
        imageView.setImageResource(currentProduct.getImagePath());

        //add actions for buttons
        findViewById(R.id.okButton).setOnClickListener(this);
        findViewById(R.id.deleteButton).setOnClickListener(this);
        findViewById(R.id.sendButton).setOnClickListener(this);


    }

    private void populateShopSpinner() {
        List<Shop> shopList = Arrays.asList(Shop.values());
        List<String> spinnerList = new ArrayList<>();
        for (Shop s : shopList) {
            spinnerList.add(s.toString());
        }
        Log.i(TAG, spinnerList.get(0) + " is first elem and size is " + spinnerList.size());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        shopSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String name = String.valueOf(nameEditText.getText());
        Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
        String brand = String.valueOf(brandEditText.getText());
        String spinnerItem = String.valueOf(shopSpinner.getSelectedItem());
        Shop shop = Shop.valueOf(spinnerItem);

        switch (v.getId()) {
            case R.id.okButton:
                Product resultProd = new Product(name, price, shop, brand, currentProduct.getImagePath());
                MainActivity.productRepository.update(currentProduct.getId(), resultProd);
                Log.i(TAG, "name: " + resultProd.getName());

                intent = new Intent(DetailsActivity.this, MainActivity.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            case R.id.deleteButton:
                intent = new Intent(DetailsActivity.this, MainActivity.class);
                if (currentProduct == null) {
                    Toast.makeText(DetailsActivity.this, "This product does not exist and it cannot be deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.productRepository.delete(currentProduct.getId());
                    Toast.makeText(DetailsActivity.this, "Removed product: " + currentProduct.toString(), Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;

            case R.id.sendButton:
                intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:bianca_cioata@yahoo.com"));
                String mailContent = "Product: \n name" + name + "\n price: " + price + " \n brand: " + brand + " \n shop: " + shop;
                intent.putExtra(Intent.EXTRA_TEXT, mailContent);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Budget Application Product");
                startActivity(intent);
                break;
        }
    }
}
