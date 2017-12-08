package com.example.ioana.budgetapplication.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
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
import android.widget.Toast;

import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.config.AppDatabase;
import com.example.ioana.budgetapplication.model.Product;
import com.example.ioana.budgetapplication.model.Supermarket;

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

        imageView = (ImageView) findViewById(R.id.addProductImageView);
        //set content for image
        imageView.setImageResource(R.drawable.noimage);

        //populate spinner
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<String> spinnerList = db.supermarketDao().getAllNames();
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
                String selected = supermarketSpinner.getSelectedItem().toString();

                final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                int supermarketId = db.supermarketDao().findByName(selected).getId();

                Product resultProd = new Product(name, price, supermarketId, brand, R.drawable.noimage);
                db.productDao().insert(resultProd);

                Log.i(TAG, "name: " + resultProd.getName());
                Intent intent = new Intent(AddActivity.this, MainActivity.class);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Button addSupermarket = findViewById(R.id.addSupermarketButton);
        addSupermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_supermarket, null);
                final EditText name = mView.findViewById(R.id.supermarketNameEditText);
                final EditText address = mView.findViewById(R.id.supermarketAddressEditText);
                Button add = mView.findViewById(R.id.saveButtonDialog);
                Button cancel = mView.findViewById(R.id.cancelButtonDialog);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nameS = name.getText().toString();
                        String addressS = address.getText().toString();

                        if (!nameS.isEmpty() && !addressS.isEmpty()) {
                            final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                            if (db.supermarketDao().getSupermarket(nameS, addressS) == null){
                                Supermarket s = new Supermarket(nameS, addressS);
                                db.supermarketDao().insert(s);
                                Log.i(TAG, "inserted supermarket with name = "+nameS+" and address= "+addressS);
                                //update spineer
                                List<String> spinnerList = db.supermarketDao().getAllNames();
                                Log.i(TAG, spinnerList.get(0) + " is first elem and size is " + spinnerList.size());
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_spinner_item, spinnerList);
                                supermarketSpinner.setAdapter(arrayAdapter);

                                dialog.dismiss();
                            }
                            else{
                                Toast.makeText(AddActivity.this, "This Supermarket already exists!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AddActivity.this, "No empty strings!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }
}
