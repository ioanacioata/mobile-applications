package com.example.ioana.budgetapplication.ui;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ioana.budgetapplication.config.MyDatabase;
import com.example.ioana.budgetapplication.model.Role;
import com.example.ioana.budgetapplication.model.Shop;
import com.example.ioana.budgetapplication.model.User;
import com.example.ioana.budgetapplication.repository.ProductRepository;
import com.example.ioana.budgetapplication.ui.adapter.ProductListAdapter;
import com.example.ioana.budgetapplication.R;
import com.example.ioana.budgetapplication.model.Product;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getName();
    public static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_ADD = 2;
    ListView listView;
    ProductListAdapter productListAdapter;

    User currentUser;
    List<Product> products;
    DatabaseReference productsRef;
    public static ProductRepository productRepository = new ProductRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = new ArrayList<>();
        productsRef = productRepository.getReference();
        productsRef.keepSynced(true);

        listView = findViewById(R.id.productList);
        displayListWithAction();

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.showChart).setOnClickListener(this);
        findViewById(R.id.showUsers).setOnClickListener(this);

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
    protected void onStart() {
        super.onStart();
        //load role of current user and modify interface accordingly
        DatabaseReference currentUserRef = MyDatabase.getDatabase().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //getting the current user and its role
                currentUser = dataSnapshot.getValue(User.class);
                if (currentUser.getRole().equals(Role.ADMIN)) {
                    findViewById(R.id.toolbarRole).setVisibility(View.VISIBLE);
                    findViewById(R.id.showUsers).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.toolbarRole).setVisibility(View.GONE);
                    findViewById(R.id.showUsers).setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //load the list of products
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products.clear();
                //load the products from the database
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Product product = d.getValue(Product.class);
                    products.add(product);
                }

                productListAdapter = new ProductListAdapter(products, getLayoutInflater());
                listView.setAdapter(productListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            case R.id.showUsers:
                finish();
                startActivity(new Intent(MainActivity.this, UsersActivity.class));
                break;
        }
    }

    private void addActionToButtonShowChart() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_graph, null);

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < Shop.values().length; i++) {
            Shop current = Shop.values()[i];
            int count = 0;
            for (Product p : products) {
                if (p.getShop().equals(current)) {
                    count++;
                }
            }
            pieEntries.add(new PieEntry(count, current.toString()));
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
}
