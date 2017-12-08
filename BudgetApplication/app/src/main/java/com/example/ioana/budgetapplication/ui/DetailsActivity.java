package com.example.ioana.budgetapplication.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
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
import com.example.ioana.budgetapplication.config.AppDatabase;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.Supermarket;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = DetailsActivity.class.getName();
    EditText nameEditText;
    EditText priceEditText;
    EditText brandEditText;
    Spinner supermarketSpinner;
    ImageView imageView;
    Product p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        p = (Product) getIntent().getSerializableExtra("product");
        Log.i(TAG, "PROD ID supermarket : " + p.toString());
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        brandEditText = (EditText) findViewById(R.id.BrandEditText);
        supermarketSpinner = findViewById(R.id.supermarketSpinner);
        imageView = (ImageView) findViewById(R.id.detailsProductImageView);

        //set text to show
        nameEditText.setText(p.getName());
        priceEditText.setText(p.getPrice().toString());
        brandEditText.setText(p.getBrand());
        populateSupermarketSpinner();
        imageView.setImageResource(p.getImagePath());

        //add actions for buttons
        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameEditText.getText());
                Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
                String brand = String.valueOf(brandEditText.getText());
                String supermarket = String.valueOf(supermarketSpinner.getSelectedItem());
                Product resultProd = new Product(name, price, getSupermarketByName(supermarket).getId(), brand, p.getImagePath());

                final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                resultProd.setId(db.productDao().findByName(p.getName()).getId());
                db.productDao().update(resultProd);
                Log.i(TAG, "name: " + resultProd.getName());

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:bianca_cioata@yahoo.com"));
                String name = String.valueOf(nameEditText.getText());
                Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
                String brand = String.valueOf(brandEditText.getText());
                String supermarket = String.valueOf(supermarketSpinner.getSelectedItem());

                String mailContent = "Product: \n name" + name + "\n price: " + price + " \n brand: " + brand + " \n supermarket: " + supermarket;
                intent.putExtra(Intent.EXTRA_TEXT, mailContent);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Budget Application Product");
                startActivity(intent);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                String name = String.valueOf(nameEditText.getText());
                Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
                String brand = String.valueOf(brandEditText.getText());
                String supermarket = String.valueOf(supermarketSpinner.getSelectedItem());
                Product p = db.productDao().findProduct(name, getSupermarketByName(supermarket).getId(), price, brand);

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                if (p==null) {
                    Toast.makeText(DetailsActivity.this,"This product does not exist and it cannot be deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    db.productDao().delete(p);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void populateSupermarketSpinner() {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<String> spinnerList = db.supermarketDao().getAllNames();

        Log.i(TAG, spinnerList.get(0) + " is first elem and size is " + spinnerList.size());

        //set values for spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        supermarketSpinner.setAdapter(arrayAdapter);

        //show selected value from spinner
        Supermarket supermarket = getSupermarketById(p.getSupermarketId());
        Log.i(TAG, " SUPERMARKET " + supermarket.toString());
        String compareValue = supermarket.getName();
        if (!compareValue.equals(null)) {
            int spinnerPosition = arrayAdapter.getPosition(compareValue);
            supermarketSpinner.setSelection(spinnerPosition);
        }
    }

    private Supermarket getSupermarketById(int id) {
        Log.i(TAG, " id " + id);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return db.supermarketDao().findById(id);
    }

    private Supermarket getSupermarketByName(String name) {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return db.supermarketDao().findByName(name);
    }
}
