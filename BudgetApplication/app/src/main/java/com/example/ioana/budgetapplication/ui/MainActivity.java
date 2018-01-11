package com.example.ioana.budgetapplication.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ioana.budgetapplication.model.Supermarket;
import com.example.ioana.budgetapplication.ui.adapter.ProductListAdapter;
import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.config.AppDatabase;
import com.example.ioana.budgetapplication.model.Product;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getName();
    public static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_ADD = 2;
    ListView listView;
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializeList();

        listView =  findViewById(R.id.productList);
        displayListWithAction();
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.showChart).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl().toString())
                    .into((ImageView) findViewById(R.id.toolbarPicture));
        }
        if (user.getDisplayName() != null) {
            TextView name = findViewById(R.id.toolbarName);
            name.setText(user.getDisplayName());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE || requestCode == REQUEST_CODE_ADD) {
            if (resultCode == RESULT_OK) {
                displayListWithAction();
            } else {
                //Toast.makeText(MainActivity.this, "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayListWithAction() {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final List<Product> products = db.productDao().loadAll();
        productListAdapter = new ProductListAdapter(products, getLayoutInflater());
        listView.setAdapter(productListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("product", products.get(position));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAdd:
                Log.i(TAG, "Add button pressed");
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
            case R.id.showChart:
                addActionToButtonShowChart();
                break;
        }
    }

    private void addActionToButtonShowChart() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_graph, null);


        //Get data for the list
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<Supermarket> supermarketList = db.supermarketDao().loadAll();

        //Populating a list of pieEntries
        Log.i(TAG, "count of " + supermarketList.get(0).getName() + " is " + db.productDao().countProducts(supermarketList.get(0).getId()));

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < supermarketList.size(); i++) {
            pieEntries.add(new PieEntry(db.productDao().countProducts(supermarketList.get(i).getId()), supermarketList.get(i).getName()));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Supermarkets in Cluj-Napoca");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12);

        PieData data = new PieData(dataSet);

        //get the Chart
        final PieChart pieChart = mView.findViewById(R.id.pieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1000);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    /**
     * Set menu options
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.menuHome:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menuProfile:
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }

        return true;
    }

    private void initializeList() {
        String name = "Auchan Iulius Mall";
        Supermarket s = new Supermarket(name, "Gheorgheni");
        String kaufland = "Kaufland";
        Supermarket s1 = new Supermarket(kaufland, "Gheorgheni");
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "budget").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        db.supermarketDao().insert(s);
        db.supermarketDao().insert(s1);

        int id = db.supermarketDao().findByName(name).getId();
        int id2 = db.supermarketDao().findByName(kaufland).getId();
        Product p1 = new Product("Coca-Cola 0.5l", 2.5, id, "Coca-Cola", R.drawable.cola5);
        Product p2 = new Product("Lapte 1l", 4.0, id2, "Zuzu", R.drawable.zuzu1l);
        Product p3 = new Product("Cafea 200 grame", 12.0, id, "Nescafe", R.drawable.nescafe);
        db.productDao().insert(p1);
        db.productDao().insert(p2);
        db.productDao().insert(p3);

    }
}
