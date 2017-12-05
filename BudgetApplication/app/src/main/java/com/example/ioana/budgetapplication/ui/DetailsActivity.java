package com.example.ioana.budgetapplication.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.config.AppDatabase;
import com.example.ioana.budgetapplication.model.Product;

public class DetailsActivity extends AppCompatActivity {
    public static final String TAG = DetailsActivity.class.getName();
    EditText nameEditText;
    EditText priceEditText;
    EditText brandEditText;
    EditText supermarketEditText;
    Product p;
    Integer pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        p = (Product) getIntent().getSerializableExtra("product");
        pos = (Integer) getIntent().getSerializableExtra("position");

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        brandEditText = (EditText) findViewById(R.id.BrandEditText);
        supermarketEditText = (EditText) findViewById(R.id.supermarketEditText);
        ImageView imageView = (ImageView) findViewById(R.id.detailsProductImageView);
        nameEditText.setText(p.getName());
        priceEditText.setText(p.getPrice().toString());
        brandEditText.setText(p.getBrand());
        supermarketEditText.setText(p.getSupermarketName());
        imageView.setImageResource(p.getImagePath());

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameEditText.getText());
                Double price = Double.valueOf(String.valueOf(priceEditText.getText()));
                String brand = String.valueOf(brandEditText.getText());
                String supermarket = String.valueOf(supermarketEditText.getText());
                Product resultProd = new Product(name, price, supermarket, brand, p.getImagePath());

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
                String supermarket = String.valueOf(supermarketEditText.getText());

                String mailContent = "Product: \n name" + name + "\n price: " + price + " \n brand: " + brand + " \n supermarket: " + supermarket;
                intent.putExtra(Intent.EXTRA_TEXT, mailContent);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Budget Application Product");
                startActivity(intent);
            }
        });
    }
}
