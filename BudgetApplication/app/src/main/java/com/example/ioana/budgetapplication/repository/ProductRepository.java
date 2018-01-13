package com.example.ioana.budgetapplication.repository;

import com.example.ioana.budgetapplication.config.MyDatabase;
import com.example.ioana.budgetapplication.model.Product;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by Ioana on 13/01/2018.
 */

/**
 * CUD operations for the Product entity, using Firebase
 */
public class ProductRepository {
    private DatabaseReference reference;

    public DatabaseReference getReference() {
        return reference;
    }

    public ProductRepository() {
        reference = MyDatabase.getDatabase().getReference("products");
    }

    public void add(Product p) {
        String id = reference.push().getKey();
        p.setId(id);
        reference.child(id).setValue(p);
    }

    public void delete(String id) {
        reference.child(id).removeValue();
    }

    public void update(String id, Product newProduct) {
        reference.child(id).child("brand").setValue(newProduct.getBrand());
        reference.child(id).child("imagePath").setValue(newProduct.getImagePath());
        reference.child(id).child("name").setValue(newProduct.getName());
        reference.child(id).child("price").setValue(newProduct.getPrice());
        reference.child(id).child("shop").setValue(newProduct.getShop());
    }
}
